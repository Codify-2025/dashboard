package Codify.dashboard.dto.response.all;

import lombok.Builder;

@Builder
public record AccumulateResponseDto
        (String id,Long from, Long to, Integer count, Double value, Double width) {}
