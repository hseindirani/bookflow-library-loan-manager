package com.nordea.bookflow.common.exception;

public class LoanAlreadyActiveException extends RuntimeException {

    public LoanAlreadyActiveException(Long memberId, Long bookId) {
        super("Member with id " + memberId + " already has an active loan for book with id " + bookId + ".");
    }
}