package com.nordea.bookflow.common;

import com.nordea.bookflow.common.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiError> handleBookNotFound(BookNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ApiError> handleMemberNotFound(MemberNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<ApiError> handleLoanNotFound(LoanNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(NoCopiesAvailableException.class)
    public ResponseEntity<ApiError> handleNoCopiesAvailable(NoCopiesAvailableException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(LoanAlreadyActiveException.class)
    public ResponseEntity<ApiError> handleLoanAlreadyActive(LoanAlreadyActiveException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(LoanAlreadyReturnedException.class)
    public ResponseEntity<ApiError> handleLoanAlreadyReturned(LoanAlreadyReturnedException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }
    @ExceptionHandler(RatingNotAllowedException.class)
    public ResponseEntity<ApiError> handleRatingNotAllowed(RatingNotAllowedException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }


    private ResponseEntity<ApiError> buildErrorResponse(HttpStatus status, String message) {
        ApiError error = new ApiError(
                LocalDateTime.now(),
                status.value(),
                message
        );

        return ResponseEntity
                .status(status)
                .body(error);
    }
}