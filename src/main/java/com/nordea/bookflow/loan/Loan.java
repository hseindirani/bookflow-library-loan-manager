package com.nordea.bookflow.loan;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("loans")
public class Loan {

    @Id
    private Long id;

    private Long memberId;
    private Long bookId;

    private LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;

    private LoanStatus status;

    public Loan() {
    }

    public Loan(Long id,
                Long memberId,
                Long bookId,
                LocalDateTime borrowedAt,
                LocalDateTime returnedAt,
                LoanStatus status) {

        this.id = id;
        this.memberId = memberId;
        this.bookId = bookId;
        this.borrowedAt = borrowedAt;
        this.returnedAt = returnedAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getBookId() {
        return bookId;
    }

    public LocalDateTime getBorrowedAt() {
        return borrowedAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void markAsReturned(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
        this.status = LoanStatus.RETURNED;
    }
    public static Loan createActiveLoan(Long memberId, Long bookId, LocalDateTime borrowedAt) {
        return new Loan(null, memberId, bookId, borrowedAt, null, LoanStatus.ACTIVE);
    }
}