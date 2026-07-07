package com.nordea.bookflow.common.exception;

public class NoCopiesAvailableException  extends RuntimeException {
    public NoCopiesAvailableException(Long bookId){
        super("Book with id " + bookId + " has no copies available.");
    }
}