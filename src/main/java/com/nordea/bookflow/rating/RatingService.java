package com.nordea.bookflow.rating;

import com.nordea.bookflow.book.BookRepository;
import com.nordea.bookflow.common.exception.BookNotFoundException;
import com.nordea.bookflow.common.exception.MemberNotFoundException;
import com.nordea.bookflow.common.exception.RatingNotAllowedException;
import com.nordea.bookflow.loan.LoanRepository;
import com.nordea.bookflow.member.MemberRepository;
import com.nordea.bookflow.rating.dto.RatingRequest;
import com.nordea.bookflow.rating.dto.RatingResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class RatingService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final RatingRepository ratingRepository;

    public RatingService(
            MemberRepository memberRepository,
            BookRepository bookRepository,
            LoanRepository loanRepository,
            RatingRepository ratingRepository
    ) {
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.ratingRepository = ratingRepository;
    }

    @Transactional
    public Mono<RatingResponse> rateBook(Long memberId, Long bookId, RatingRequest request) {
        return memberRepository.findById(memberId)
                .switchIfEmpty(Mono.error(new MemberNotFoundException(memberId)))
                .then(bookRepository.findById(bookId)
                        .switchIfEmpty(Mono.error(new BookNotFoundException(bookId))))
                .then(validateMemberHasBorrowedBook(memberId, bookId))
                .then(Mono.defer(() ->
                        ratingRepository.findByMemberIdAndBookId(memberId, bookId)
                                .flatMap(existingRating -> updateExistingRating(existingRating, request))
                                .switchIfEmpty(Mono.defer(() -> createNewRating(memberId, bookId, request)))
                ));
    }

    private Mono<Void> validateMemberHasBorrowedBook(Long memberId, Long bookId) {
        return loanRepository.existsByMemberIdAndBookId(memberId, bookId)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new RatingNotAllowedException(memberId, bookId));
                    }

                    return Mono.empty();
                });
    }

    private Mono<RatingResponse> updateExistingRating(Rating rating, RatingRequest request) {
        rating.updateRating(
                request.score(),
                request.feedback(),
                LocalDateTime.now()
        );

        return ratingRepository.save(rating)
                .map(this::toResponse);
    }

    private Mono<RatingResponse> createNewRating(Long memberId, Long bookId, RatingRequest request) {
        Rating rating = Rating.createNewRating(
                memberId,
                bookId,
                request.score(),
                request.feedback(),
                LocalDateTime.now()
        );

        return ratingRepository.save(rating)
                .map(this::toResponse);
    }

    private RatingResponse toResponse(Rating rating) {
        return new RatingResponse(
                rating.getId(),
                rating.getMemberId(),
                rating.getBookId(),
                rating.getScore(),
                rating.getFeedback(),
                rating.getCreatedAt(),
                rating.getUpdatedAt()
        );
    }
}