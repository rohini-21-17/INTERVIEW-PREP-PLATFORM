package com.interviewprep.controller;

import com.interviewprep.dto.DashboardStats;
import com.interviewprep.model.User;
import com.interviewprep.service.DashboardService;
import com.interviewprep.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Performance analytics and progress tracking")
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {

    private final DashboardService dashboardService;
    private final UserService userService;

    @GetMapping("/stats")
    @Operation(summary = "Get performance stats for the logged-in user")
    public ResponseEntity<DashboardStats> getStats(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        DashboardStats stats = dashboardService.getStatsForUser(user);
        return ResponseEntity.ok(stats);
    }
}