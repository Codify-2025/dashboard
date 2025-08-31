package Codify.dashboard.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Entity
@Table(name = "Submission")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submissionId")
    private Long submissionId;

    @Column(name = "assignmentId")
    private Long assignmentId;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "week")
    private Long week;

    @Column(name = "submissionDate")
    private LocalDateTime submissionDate;

    @Column(name = "studentId")
    private Long studentId;

    @Column(name = "studentName")
    private String studentName;

}
