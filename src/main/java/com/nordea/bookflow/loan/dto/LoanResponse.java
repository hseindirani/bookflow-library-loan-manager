package com.nordea.bookflow.loan.dto;

import com.nordea.bookflow.loan.LoanStatus;

import java.time.LocalDateTime;

public record LoanResponse(
        Long loanId,
        Long bookId,
        Long memberId,
        LoanStatus status,
        LocalDateTime borrowedAt,
        LocalDateTime returnedAt
) {
}