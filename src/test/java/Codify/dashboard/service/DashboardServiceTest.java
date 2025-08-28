package Codify.dashboard.service;


import Codify.dashboard.dto.response.StudentResponseDto;
import Codify.dashboard.dto.response.all.AccumulateResponseDto;
import Codify.dashboard.dto.response.all.DashboardAllResponseDto;
import Codify.dashboard.dto.response.all.FindResultDto;
import Codify.dashboard.dto.response.week.AccumulateWeekResponseDto;
import Codify.dashboard.dto.response.week.DashboardWeekResponseDto;
import Codify.dashboard.dto.response.week.FindWeekResultDto;
import Codify.dashboard.dto.response.week.WeekDataDto;
import Codify.dashboard.repository.AssignmentRepository;
import Codify.dashboard.repository.SubmissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class DashboardServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private SubmissionRepository submissionRepository;

    @InjectMocks
    private DashboardService dashboardService;


    UUID testUserUuid;
    Long testSubjectId;
    List<FindResultDto> rawData;
    List<FindWeekResultDto> weekData;
    List<StudentResponseDto> studentData;

    @BeforeEach
    void setUp() {

        testUserUuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        testSubjectId = 1L;

        //더미 rawData
        rawData = Arrays.asList(
                new FindResultDto(2000000L, 2000001L, 0.9, 1L),  // week 1
                new FindResultDto(2000000L, 2000001L, 0.9, 2L),  // week 2 -같은 학생 쌍
                new FindResultDto(2000001L, 2000002L, 0.8, 1L),  // week 1
                new FindResultDto(2000001L, 2000002L, 0.6, 2L),  // week 2 -같은 학생 쌍
                new FindResultDto(2000002L, 2000003L, 0.5, 1L)   // week 1 - 0.8 미만
        );

        weekData = Arrays.asList(
                new FindWeekResultDto() {
                    @Override
                    public Long getStudentFromId() {
                        return 2000000L;
                    }

                    @Override
                    public Long getStudentToId() {
                        return 2000001L;
                    }

                    @Override
                    public Double getAccumulateResult() {
                        return 0.9;
                    }

                    @Override
                    public Long getWeek() {
                        return 1L;
                    }

                    @Override
                    public LocalDateTime getSubmissionFromTime() {
                        return null;
                    }

                    @Override
                    public LocalDateTime getSubmissionToTime() {
                        return null;
                    }
                }
        );

        studentData = Arrays.asList(
                new StudentResponseDto(2000000L, "studentA"),
                new StudentResponseDto(2000001L, "studentB"),
                new StudentResponseDto(2000002L, "studentC"),
                new StudentResponseDto(2000003L, "studentD")
        );
    }

    @Test
    @DisplayName("getAccumulateAll이 올바르게 실행되는지 테스트")
    void getAccumulateAll_Success() {
        //given-repository 호출 시 리턴하는 데이터
        when(assignmentRepository.findResultData(testUserUuid, testSubjectId))
                .thenReturn(rawData);

        when(submissionRepository.findStudentData(testUserUuid, testSubjectId)
        ).thenReturn(studentData);

        //when
        DashboardAllResponseDto result = dashboardService.getAccumulateAll(testUserUuid, testSubjectId);

        //then
        assertThat(result.edges()).extracting(AccumulateResponseDto::id).contains("2000000-2000001");
        assertThat(result.edges()).extracting(AccumulateResponseDto::id).size().isEqualTo(3);
        assertThat(result.edges()).extracting(AccumulateResponseDto::count).contains(2);
        assertThat(result.edges()).extracting(AccumulateResponseDto::value).contains(0.7);
    }

    @Test
    @DisplayName("getWeekAccumulate가 올바르게 실행되는지 테스트")
    void getWeekAccumulate_Success() {
        //given
        when(assignmentRepository.findWeekResults(testUserUuid.toString(), testSubjectId))
        .thenReturn(weekData);
        when(submissionRepository.findStudentData(testUserUuid, testSubjectId)
        ).thenReturn(studentData);

        //when
        DashboardWeekResponseDto result = dashboardService.getWeekAccumulate(testUserUuid, testSubjectId);

        //then
        assertThat(result.nodes()).extracting(StudentResponseDto::label).contains("studentA");
        assertThat(result.edges()).extracting(WeekDataDto::week).contains(1L);
        assertThat(result.edges()).flatExtracting(WeekDataDto::data).extracting(AccumulateWeekResponseDto::id).contains("2000000-2000001");

    }
}
