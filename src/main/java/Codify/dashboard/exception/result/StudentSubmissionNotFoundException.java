package Codify.dashboard.exception.result;


import Codify.dashboard.exception.ErrorCode;
import Codify.dashboard.exception.baseException.BaseException;

public class StudentSubmissionNotFoundException extends BaseException {
    public StudentSubmissionNotFoundException() {
        super(ErrorCode.STUDENT_SUBMISSION_NOT_FOUND);
    }
}
