package com.nordea.bookflow.dashboard.dto;

import java.util.List;

public record DashboardDto(
        Long memberId,
        List<DashboardItemDto> items
) {
}