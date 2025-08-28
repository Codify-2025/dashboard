package Codify.dashboard.repository;

import Codify.dashboard.domain.Submission;
import Codify.dashboard.dto.response.StudentResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    @Query("SELECT DISTINCT s.studentId, s.studentName " +
            "FROM Submission s " +
            "INNER JOIN Assignment a on s.assignmentId = a.assignmentId " +
            "WHERE a.userUuid = :userUuid AND a.subjectId = :subjectId")
    List<StudentResponseDto> findStudentData(UUID userUuid, Long subjectId);

}
