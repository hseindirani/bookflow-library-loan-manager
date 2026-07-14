package com.nordea.bookflow.loan;

import com.nordea.bookflow.book.Book;
import com.nordea.bookflow.book.BookRepository;
import com.nordea.bookflow.common.exception.*;
import com.nordea.bookflow.loan.dto.LoanResponse;
import com.nordea.bookflow.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class LoanService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private  final LoanRepository loanRepository;
    public LoanService(BookRepository bookRepository,MemberRepository memberRepository, LoanRepository loanRepository){
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
    }

    @Transactional
    public Mono<LoanResponse> borrowBook(Long memberId, Long bookId) {
        return memberRepository.findById(memberId)
                .switchIfEmpty(Mono.error(new MemberNotFoundException(memberId)))
                .then(bookRepository.findById(bookId)
                        .switchIfEmpty(Mono.error(new BookNotFoundException(bookId))))
                .flatMap(book ->
                        validateNoActiveLoan(memberId, bookId)
                                .then(validateBookIsAvailable(book))
                                .then(Mono.defer(() -> createLoan(memberId, book))) // Create the loan only after all validations complete successfully
                );
    }

    @Transactional
    public Mono<LoanResponse> returnBook(Long memberId, Long loanId) {
        return memberRepository.findById(memberId)
                .switchIfEmpty(Mono.error(new MemberNotFoundException(memberId)))
                .then(loanRepository.findById(loanId)
                        .switchIfEmpty(Mono.error(new LoanNotFoundException(loanId))))
                .flatMap(loan ->
                        validateLoanCanBeReturned(loan, memberId, loanId)
                                .then(processReturn(loan))
                );
    }
    private Mono<Void> validateBookIsAvailable(Book book) {
        if (book.getAvailableCopies() <= 0) {
            return Mono.error(new NoCopiesAvailableException(book.getId()));
        }

        return Mono.empty();
    }
    private Mono<Void> validateNoActiveLoan(Long memberId, Long bookId) {
        return loanRepository.existsByMemberIdAndBookIdAndStatus(
                        memberId,
                        bookId,
                        LoanStatus.ACTIVE
                )
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new LoanAlreadyActiveException(memberId, bookId));
                    }

                    return Mono.empty();
                });
    }
    private Mono<LoanResponse> createLoan(Long memberId, Book book) {
        book.decreaseAvailableCopies();

        Loan loan = Loan.createActiveLoan(
                memberId,
                book.getId(),
                LocalDateTime.now()
        );

        return bookRepository.save(book)
                .then(loanRepository.save(loan))
                .map(this::toResponse);
    }
    private Mono<Void> validateLoanCanBeReturned(Loan loan, Long memberId, Long loanId) {
        if (!loan.getMemberId().equals(memberId)) {
            return Mono.error(new LoanNotFoundException(loanId));
        }

        if (loan.getStatus() == LoanStatus.RETURNED) {
            return Mono.error(new LoanAlreadyReturnedException(loanId));
        }

        return Mono.empty();
    }
    private Mono<LoanResponse> processReturn(Loan loan) {
        loan.markAsReturned(LocalDateTime.now());

        return bookRepository.findById(loan.getBookId())
                .switchIfEmpty(Mono.error(new BookNotFoundException(loan.getBookId())))
                .flatMap(book -> {
                    book.increaseAvailableCopies();

                    return bookRepository.save(book)
                            .then(loanRepository.save(loan))
                            .map(this::toResponse);
                });
    }
    private LoanResponse toResponse(Loan loan) {
        return new LoanResponse(
                loan.getId(),
                loan.getBookId(),
                loan.getMemberId(),
                loan.getStatus(),
                loan.getBorrowedAt(),
                loan.getReturnedAt()
        );
    }


}
