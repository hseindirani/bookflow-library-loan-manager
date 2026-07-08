package com.nordea.bookflow.dashboard;

import com.nordea.bookflow.dashboard.dto.DashboardDto;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/members/{memberId}/dashboard")
    public Mono<DashboardDto> getDashboard(@PathVariable Long memberId) {
        return dashboardService.getDashboard(memberId);
    }
}