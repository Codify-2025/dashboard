package Codify.dashboard.dto.response.week;

import Codify.dashboard.dto.response.StudentResponseDto;

import java.util.List;

public record DashboardWeekResponseDto(List<StudentResponseDto> nodes, List<WeekDataDto> edges)
{}
