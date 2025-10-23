package Codify.dashboard.dto.result.topology;
import java.util.List;

public record TopologyEdgeDto(
        String id,
        String from,
        String to,
        Double value,
        String comparedFiles,
        List<HistoryDto> histories
) {}