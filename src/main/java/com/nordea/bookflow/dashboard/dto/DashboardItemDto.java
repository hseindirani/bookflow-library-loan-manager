package com.nordea.bookflow.dashboard.dto;

import java.time.LocalDateTime;

public record DashboardItemDto(
        Long bookId,
        String title,
        String author,
        LocalDateTime borrowedAt,
        Integer myRating,
        Double averageRating
) {
}