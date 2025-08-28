package Codify.dashboard.controller;

import Codify.dashboard.dto.response.all.DashboardAllResponseDto;
import Codify.dashboard.dto.response.data.DashboardDataDto;
import Codify.dashboard.dto.response.week.DashboardWeekResponseDto;
import Codify.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/accumulate")
    public ResponseEntity<DashboardAllResponseDto> getAccumulate
            (@RequestHeader("USER-UUID") String userUuidHeader, @RequestParam Long subjectId)
    {
        final UUID userUuid = UUID.fromString(userUuidHeader);
        return ResponseEntity.ok(dashboardService.getAccumulateAll(userUuid,subjectId));

    }

    @GetMapping("/record")
    public ResponseEntity<DashboardWeekResponseDto> getWeekAccumulate
            (@RequestHeader("USER-UUID") String userUuidHeader, @RequestParam Long subjectId)
    {
        final UUID userUuid = UUID.fromString(userUuidHeader);
        return ResponseEntity.ok(dashboardService.getWeekAccumulate(userUuid,subjectId));

    }

    @GetMapping("/main")
    public ResponseEntity<DashboardDataDto> getDashboardData
            (@RequestHeader("USER-UUID") String userUuidHeader)
    {
        final UUID userUuid = UUID.fromString(userUuidHeader);
        return ResponseEntity.ok(dashboardService.getDashboardData(userUuid));

    }


}
