package com.nordea.bookflow.common.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(Long memberId){
        super("Member with id " + memberId + " was not found.");
    }
}
