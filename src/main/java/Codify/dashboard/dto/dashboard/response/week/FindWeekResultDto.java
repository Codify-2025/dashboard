package Codify.dashboard.dto.dashboard.response.week;

import java.time.LocalDateTime;

//native query dto 반환을 위한 jpa projection
public interface FindWeekResultDto {
    Long getStudentFromId();
    Long getStudentToId();
    Double getAccumulateResult();
    Long getWeek();
    LocalDateTime getSubmissionFromTime();
    LocalDateTime getSubmissionToTime();
}


