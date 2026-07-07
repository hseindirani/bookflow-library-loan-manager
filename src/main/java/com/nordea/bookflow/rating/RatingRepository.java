package com.nordea.bookflow.rating;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RatingRepository extends ReactiveCrudRepository<Rating, Long> {

    Mono<Rating> findByMemberIdAndBookId(Long memberId, Long bookId);

    Flux<Rating> findByBookId(Long bookId);
}