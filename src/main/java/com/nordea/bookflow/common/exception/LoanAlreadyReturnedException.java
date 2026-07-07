package com.nordea.bookflow.common.exception;

public class LoanAlreadyReturnedException extends RuntimeException {

    public LoanAlreadyReturnedException(Long loanId) {
        super("Loan with id " + loanId + " is already returned.");
    }
}