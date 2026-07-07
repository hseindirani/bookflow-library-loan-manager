package com.nordea.bookflow.loan;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LoanRepository extends ReactiveCrudRepository<Loan, Long> {

    //Check if member already has ACTIVE loan for this book
    Mono<Loan> findByMemberIdAndBookIdAndStatus(Long memberId, Long bookId, LoanStatus status);


    //Find active loans for a member
    Flux<Loan> findByMemberIdAndStatus(Long memberId, LoanStatus status);

    //Check if member has borrowed this book at least once
    Flux<Loan> findByMemberIdAndBookId(Long memberId, Long bookId);

    //Check if member already has ACTIVE loan for this book
    Mono<Boolean> existsByMemberIdAndBookIdAndStatus(
            Long memberId,
            Long bookId,
            LoanStatus status);
}