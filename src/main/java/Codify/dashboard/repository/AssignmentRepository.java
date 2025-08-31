package Codify.dashboard.repository;

import Codify.dashboard.domain.Assignment;
import Codify.dashboard.dto.response.all.FindResultDto;
import Codify.dashboard.dto.response.week.FindWeekResultDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AssignmentRepository extends JpaRepository<Assignment,Long> {

    @Query("SELECT r.studentFromId, r.studentToId, r.accumulateResult, a.week " +
            "FROM Assignment a INNER JOIN Result r ON a.assignmentId = r.assignmentId " +
            "WHERE a.userUuid = :userUuid AND a.subjectId = :subjectId")
    List<FindResultDto> findResultData(UUID userUuid, Long subjectId);

    //복잡한 query -> native query 사용
    @Query(value = """
        SELECT 
            r.student_from_id AS studentFromId,
            r.student_to_id AS studentToId,
            r.accumulateResult AS accumulateResult,
            a.week AS week,
            s_from.submissionDate AS submissionFromTime,
            s_to.submissionDate AS submissionToTime
        FROM Assignment a 
        INNER JOIN Result r ON a.assignmentId = r.assignmentId
        INNER JOIN Submission s_from ON (a.assignmentId = s_from.assignmentId AND r.student_from_id = s_from.studentId)
        INNER JOIN Submission s_to ON (a.assignmentId = s_to.assignmentId AND r.student_to_id = s_to.studentId)
        WHERE a.userUuid = UUID_TO_BIN(:userUuid) 
          AND a.subjectId = :subjectId
        """, nativeQuery = true)
    List<FindWeekResultDto> findWeekResults(
            @Param("userUuid") String userUuid,
            @Param("subjectId") Long subjectId
    );

    @Query("SELECT count (*) from Assignment where userUuid =:userUuid")
    Integer AssignmentCount(UUID userUuid);
}
