
package com.nordea.bookflow.loan;

import com.nordea.bookflow.book.Book;
import com.nordea.bookflow.book.BookRepository;
import com.nordea.bookflow.common.exception.NoCopiesAvailableException;
import com.nordea.bookflow.loan.dto.LoanResponse;
import com.nordea.bookflow.member.Member;
import com.nordea.bookflow.member.MemberRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoanServiceTest {

    private final BookRepository bookRepository = mock(BookRepository.class);
    private final MemberRepository memberRepository = mock(MemberRepository.class);
    private final LoanRepository loanRepository = mock(LoanRepository.class);

    private final LoanService loanService = new LoanService(
            bookRepository,
            memberRepository,
            loanRepository
    );

    @Test
    void borrowBook_shouldFail_whenNoCopiesAvailable() {
        // Arrange
        Long memberId = 1L;
        Long bookId = 2L;

        Member member = new Member(memberId, "Hussein", "hussein@example.com");

        Book book = new Book(
                bookId,
                "Clean Code",
                "Robert C. Martin",
                "9780132350884",
                1,
                0
        );

        when(memberRepository.findById(memberId))
                .thenReturn(Mono.just(member));

        when(bookRepository.findById(bookId))
                .thenReturn(Mono.just(book));

        when(loanRepository.existsByMemberIdAndBookIdAndStatus(
                memberId,
                bookId,
                LoanStatus.ACTIVE
        )).thenReturn(Mono.just(false));

        // Act
        Mono<LoanResponse> result = loanService.borrowBook(memberId, bookId);

        // Assert
        StepVerifier.create(result)
                .expectError(NoCopiesAvailableException.class)
                .verify();
    }
}