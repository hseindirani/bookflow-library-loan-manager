package com.nordea.bookflow.rating.dto;

import java.time.LocalDateTime;

public record RatingResponse(
        Long ratingId,
        Long memberId,
        Long bookId,
        Integer score,
        String feedback,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}