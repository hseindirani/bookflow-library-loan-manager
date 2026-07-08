package com.nordea.bookflow.common.exception;

public class RatingNotAllowedException extends RuntimeException {

    public RatingNotAllowedException(Long memberId, Long bookId) {
        super("Member with id " + memberId + " is not allowed to rate book with id " + bookId + " because they have not borrowed it.");
    }
}