package com.nordea.bookflow.rating;

import com.nordea.bookflow.book.Book;
import com.nordea.bookflow.book.BookRepository;
import com.nordea.bookflow.common.exception.RatingNotAllowedException;
import com.nordea.bookflow.loan.LoanRepository;
import com.nordea.bookflow.member.Member;
import com.nordea.bookflow.member.MemberRepository;
import com.nordea.bookflow.rating.dto.RatingRequest;
import com.nordea.bookflow.rating.dto.RatingResponse;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RatingServiceTest {

    private final MemberRepository memberRepository = mock(MemberRepository.class);
    private final BookRepository bookRepository = mock(BookRepository.class);
    private final LoanRepository loanRepository = mock(LoanRepository.class);
    private final RatingRepository ratingRepository = mock(RatingRepository.class);

    private final RatingService ratingService = new RatingService(
            memberRepository,
            bookRepository,
            loanRepository,
            ratingRepository
    );

    @Test
    void rateBook_shouldFail_whenMemberNeverBorrowedBook() {
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
                1
        );

        RatingRequest request = new RatingRequest(memberId, 5, "Great book");

        when(memberRepository.findById(memberId))
                .thenReturn(Mono.just(member));

        when(bookRepository.findById(bookId))
                .thenReturn(Mono.just(book));

        when(loanRepository.existsByMemberIdAndBookId(memberId, bookId))
                .thenReturn(Mono.just(false));

        // Act
        Mono<RatingResponse> result = ratingService.rateBook(memberId, bookId, request);

        // Assert
        StepVerifier.create(result)
                .expectError(RatingNotAllowedException.class)
                .verify();
    }
}