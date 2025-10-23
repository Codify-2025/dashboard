package Codify.dashboard.dto.result;

import java.util.List;

public record FilterPairsGroupDto(List<FilteredPairsDto> aboveThreshold, List<FilteredPairsDto> belowThreshold) {
}