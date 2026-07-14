package com.nordea.bookflow.loan;

import com.nordea.bookflow.loan.dto.BorrowBookRequest;
import com.nordea.bookflow.loan.dto.LoanResponse;
import com.nordea.bookflow.loan.dto.ReturnBookRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    // In a production system, Spring Security would authenticate the user,
    // issue a JWT after login, and the member identity would be extracted
    // from the token instead of trusting a request parameter or body.

    @PostMapping("/books/{bookId}/loans")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<LoanResponse> borrowBook(
            @PathVariable Long bookId,
            @RequestBody BorrowBookRequest request
    ) {
        return loanService.borrowBook(request.memberId(), bookId);
    }

    @PostMapping("/loans/{loanId}/return")
    public Mono<LoanResponse> returnBook(
            @PathVariable Long loanId,
            @RequestBody ReturnBookRequest request
    ) {
        return loanService.returnBook(request.memberId(), loanId);
    }
}