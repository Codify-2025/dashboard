package Codify.dashboard.service;

import Codify.dashboard.dto.response.*;
import Codify.dashboard.dto.response.all.AccumulateResponseDto;
import Codify.dashboard.dto.response.all.DashboardAllResponseDto;
import Codify.dashboard.dto.response.all.FindResultDto;
import Codify.dashboard.dto.response.data.DashboardDataDto;
import Codify.dashboard.dto.response.data.SubjectDataDto;
import Codify.dashboard.dto.response.data.UserDataDto;
import Codify.dashboard.dto.response.week.AccumulateWeekResponseDto;
import Codify.dashboard.dto.response.week.DashboardWeekResponseDto;
import Codify.dashboard.dto.response.week.FindWeekResultDto;
import Codify.dashboard.dto.response.week.WeekDataDto;
import Codify.dashboard.repository.AssignmentRepository;
import Codify.dashboard.repository.SubjectRepository;
import Codify.dashboard.repository.SubmissionRepository;
import Codify.dashboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private static  final double SIMILARITY_THRESHOLD = 0.8;
    private static  final double WEIGHT_MULTIPLIER = 10;

    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    //누적 유사도
    @Transactional(readOnly = true)
    public DashboardAllResponseDto getAccumulateAll(UUID userUuid, Long subjectId) {
        //데이터베이스 조회 -> 학생 정보 조회 + 누적 유사도 계산 -> 리턴
        //1. 데이터 조회
        List<FindResultDto> rawData = assignmentRepository.findResultData(userUuid, subjectId);

        //2. 그룹화 -> 학번이 asc로 정렬되었다 가정하고 수행
        //studentFromId + studentToId로 그룹화 후 전체 평균 계산
        Map<String, Double> averageResults = rawData.stream()
                .collect(Collectors.groupingBy(
                        dto -> dto.studentFromId() + "-" + dto.studentToId(), //key -> "200000-2000001"
                        Collectors.averagingDouble(FindResultDto::accumulateResult)
                ));

        //3. SIMILARITY_THRESHOLD이상인 데이터 개수 계산
        Map<String, Long> counts = rawData.stream()
                .filter(dto -> dto.accumulateResult() > SIMILARITY_THRESHOLD)
                .collect(Collectors.groupingBy(
                        dto -> dto.studentFromId() + "-" + dto.studentToId(),
                        Collectors.counting()
                ));
        //4. AccumulateResponseDto 리스트 생성
        List<AccumulateResponseDto> accumulateList =
                averageResults.entrySet().stream()
                        .map(entry -> {
                            String[] ids = entry.getKey().split("-");
                            String key = entry.getKey();
                            Long count = counts.getOrDefault(key, 0L);

                            return new AccumulateResponseDto(
                                    key,
                                    Long.parseLong(ids[0]),
                                    Long.parseLong(ids[1]),
                                    count.intValue(),
                                    entry.getValue(),
                                    entry.getValue() * WEIGHT_MULTIPLIER
                            );
                        })
                        .collect(Collectors.toList());
        //5. studentResponseDto리스트 생성
        List<StudentResponseDto> studentList = submissionRepository.findStudentData(userUuid, subjectId);

        return new DashboardAllResponseDto(studentList, accumulateList);

    }


    //해당 과목에 대한 주차별 유사도
    @Transactional(readOnly = true)
    public DashboardWeekResponseDto getWeekAccumulate(UUID userUuid, Long subjectId) {

        List<StudentResponseDto> studentList = submissionRepository.findStudentData(userUuid, subjectId);
        List<FindWeekResultDto> rawData = assignmentRepository.findWeekResults(userUuid.toString(), subjectId);

        //1. 주차별로 그룹화
        List<WeekDataDto> weekDataList = rawData.stream()
                .collect(Collectors.groupingBy(FindWeekResultDto::getWeek))
                .entrySet().stream()
                .map(weekEntry -> {
                    Long week = weekEntry.getKey();

                    //week에 해당하는 데이터
                    List<AccumulateWeekResponseDto> edgeList =
                            weekEntry.getValue().stream()
                                    .map(dto -> new AccumulateWeekResponseDto(
                                            dto.getStudentFromId() + "-" + dto.getStudentToId(),
                                            dto.getStudentFromId(),
                                            dto.getStudentToId(),
                                            dto.getSubmissionFromTime(),
                                            dto.getSubmissionToTime(),
                                            dto.getAccumulateResult(),
                                            dto.getAccumulateResult() *  WEIGHT_MULTIPLIER
                                    ))
                                    .collect(Collectors.toList());
                    return new WeekDataDto(week, edgeList);
                })
                .sorted(Comparator.comparing(WeekDataDto::week))
                .collect(Collectors.toList());

        return new DashboardWeekResponseDto(studentList, weekDataList);
    }

    @Transactional(readOnly = true)
    public DashboardDataDto getDashboardData(UUID userUuid) {

        UserDataDto userData = userRepository.findByUserUuid(userUuid);
        List<SubjectDataDto> subjectDataList = subjectRepository.findByUserUuid(userUuid);
        Integer testCount = assignmentRepository.AssignmentCount(userUuid);

        return new DashboardDataDto(userData, testCount, subjectDataList);
    }
}
