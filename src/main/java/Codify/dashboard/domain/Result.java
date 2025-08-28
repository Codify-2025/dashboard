package Codify.dashboard.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "Result")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resultId")
    private Long resultId;

    @Column(name = "submission_from_id")
    private Long submissionFromId;

    @Column(name = "submission_to_id")
    private Long submissionToId;

    @Column(name = "student_from_id")
    private Long studentFromId;

    @Column(name = "student_to_id")
    private Long studentToId;

    @Column(name = "accumulateResult")
    private Double accumulateResult;

    @Column(name = "assignmentId")
    private Long assignmentId;

}
