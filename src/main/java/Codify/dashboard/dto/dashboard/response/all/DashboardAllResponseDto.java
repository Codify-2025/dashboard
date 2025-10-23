package Codify.dashboard.dto.dashboard.response.all;



import Codify.dashboard.dto.dashboard.response.StudentDashboardResponseDto;

import java.util.List;

public record DashboardAllResponseDto
        (List<StudentDashboardResponseDto> nodes, List<AccumulateResponseDto> edges) {}
