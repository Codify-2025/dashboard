package Codify.dashboard.dto.result;


import java.util.List;

public record ResultGraphDto(List<StudentResponseDto> nodes, FilterSummaryDto filterSummary, FilterPairsGroupDto filterPairs) {
}