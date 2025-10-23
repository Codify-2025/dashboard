package Codify.dashboard.dto.dashboard.response.all;

import lombok.Builder;

@Builder
public record AccumulateResponseDto
        (String id,Long from, Long to, Integer count, Double value, Double width) {}
