package com.nordea.bookflow.rating;

import com.nordea.bookflow.rating.dto.RatingRequest;
import com.nordea.bookflow.rating.dto.RatingResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PutMapping("/books/{bookId}/ratings")
    public Mono<RatingResponse> rateBook(
            @PathVariable Long bookId,
            @Valid @RequestBody RatingRequest request
    ) {
        return ratingService.rateBook(request.memberId(), bookId, request);
    }
}