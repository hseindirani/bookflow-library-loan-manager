package com.nordea.bookflow.common;

import java.time.LocalDateTime;

public record ApiError(
        LocalDateTime timestamp,
        int status,
        String message
) {
}
