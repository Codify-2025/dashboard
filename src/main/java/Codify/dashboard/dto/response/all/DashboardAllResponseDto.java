package Codify.dashboard.dto.response.all;


import Codify.dashboard.dto.response.StudentResponseDto;

import java.util.List;

public record DashboardAllResponseDto
        (List<StudentResponseDto> nodes, List<AccumulateResponseDto> edges) {}
