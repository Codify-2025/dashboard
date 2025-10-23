package Codify.dashboard.dto.dashboard.response.week;

import java.util.List;

public record WeekDataDto(Long week, List<AccumulateWeekResponseDto> data) { }
