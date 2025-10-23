package Codify.dashboard.dto.result;

public record FilterSummaryDto(Integer total,Integer aboveThreshold, Integer belowThreshold, Double threshold ) {
}
