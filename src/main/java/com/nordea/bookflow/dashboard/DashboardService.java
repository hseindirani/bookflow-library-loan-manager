package com.nordea.bookflow.dashboard;

import com.nordea.bookflow.book.Book;
import com.nordea.bookflow.book.BookRepository;
import com.nordea.bookflow.common.exception.BookNotFoundException;
import com.nordea.bookflow.common.exception.MemberNotFoundException;
import com.nordea.bookflow.dashboard.dto.DashboardDto;
import com.nordea.bookflow.dashboard.dto.DashboardItemDto;
import com.nordea.bookflow.loan.Loan;
import com.nordea.bookflow.loan.LoanRepository;
import com.nordea.bookflow.loan.LoanStatus;
import com.nordea.bookflow.member.MemberRepository;
import com.nordea.bookflow.rating.Rating;
import com.nordea.bookflow.rating.RatingRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class DashboardService {

    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final RatingRepository ratingRepository;

    public DashboardService(MemberRepository memberRepository,
                            LoanRepository loanRepository,
                            BookRepository bookRepository,
                            RatingRepository ratingRepository) {
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.ratingRepository = ratingRepository;
    }

    public Mono<DashboardDto> getDashboard(Long memberId) {
        return memberRepository.findById(memberId)
                .switchIfEmpty(Mono.error(new MemberNotFoundException(memberId)))
                .thenMany(loanRepository.findByMemberIdAndStatus(memberId, LoanStatus.ACTIVE))
                .flatMap(this::buildDashboardItem)
                .collectList()
                .map(items -> new DashboardDto(memberId, items));
    }

    private Mono<DashboardItemDto> buildDashboardItem(Loan loan) {
        Mono<Book> bookMono = bookRepository.findById(loan.getBookId())
                .switchIfEmpty(Mono.error(new BookNotFoundException(loan.getBookId())));

        Mono<Optional<Integer>> myRatingMono = ratingRepository.findByMemberIdAndBookId(
                        loan.getMemberId(),
                        loan.getBookId()
                )
                .map(rating -> Optional.of(rating.getScore()))
                .defaultIfEmpty(Optional.empty());

        Mono<Optional<Double>> averageRatingMono = ratingRepository.findByBookId(loan.getBookId())
                .map(Rating::getScore)
                .collectList()
                .map(this::calculateAverageRating);

        return Mono.zip(bookMono, myRatingMono, averageRatingMono)
                .map(tuple -> {
                    Book book = tuple.getT1();
                    Integer myRating = tuple.getT2().orElse(null);
                    Double averageRating = tuple.getT3().orElse(null);

                    return new DashboardItemDto(
                            book.getId(),
                            book.getTitle(),
                            book.getAuthor(),
                            loan.getBorrowedAt(),
                            myRating,
                            averageRating
                    );
                });

    }

    private Optional<Double> calculateAverageRating(List<Integer> scores) {
        if (scores.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(
                scores.stream()
                        .mapToInt(Integer::intValue)
                        .average()
                        .orElse(0.0)
        );
    }
}