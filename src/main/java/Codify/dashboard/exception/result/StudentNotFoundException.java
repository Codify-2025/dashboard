package Codify.dashboard.exception.result;

import Codify.dashboard.exception.ErrorCode;
import Codify.dashboard.exception.baseException.BaseException;

public class StudentNotFoundException extends BaseException {
    public StudentNotFoundException() {
        super(ErrorCode.STUDENT_NOT_FOUND);
    }
}