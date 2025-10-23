package Codify.dashboard.dto.dashboard.response.week;

import Codify.dashboard.dto.dashboard.response.StudentDashboardResponseDto;

import java.util.List;

public record DashboardWeekResponseDto(List<StudentDashboardResponseDto> nodes, List<WeekDataDto> edges)
{}
