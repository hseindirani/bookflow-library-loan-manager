package com.nordea.bookflow.common.exception;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(Long LoanId){
        super("Loan with id " + LoanId + " was not found.");
    }
}