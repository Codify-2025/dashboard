package Codify.dashboard.dto.response.week;

import Codify.dashboard.dto.response.all.AccumulateResponseDto;

import java.util.List;

public record WeekDataDto(Long week, List<AccumulateWeekResponseDto> data) { }
