package Codify.dashboard.dto.response.week;

import java.time.LocalDateTime;

public record AccumulateWeekResponseDto
        (String id, Long from, Long to, LocalDateTime submittedFrom,LocalDateTime submittedTo, Double value, Double width) {
}
