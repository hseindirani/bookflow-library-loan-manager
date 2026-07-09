package com.nordea.bookflow.loan;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoanControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void borrowBook_shouldCreateLoan_whenRequestIsValid() {
        webTestClient.post()
                .uri("/api/books/{bookId}/loans", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                    {
                        "memberId": 1
                    }
                    """)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.loanId").exists()
                .jsonPath("$.bookId").isEqualTo(1)
                .jsonPath("$.memberId").isEqualTo(1)
                .jsonPath("$.status").isEqualTo("ACTIVE")
                .jsonPath("$.borrowedAt").exists();
    }
    @Test
    void borrowBook_shouldReturn409_whenNoCopiesAvailable() {
        webTestClient.post()
                .uri("/api/books/{bookId}/loans", 3L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                    {
                        "memberId": 1
                    }
                    """)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody()
                .jsonPath("$.status").isEqualTo(409)
                .jsonPath("$.message").exists();
    }
}