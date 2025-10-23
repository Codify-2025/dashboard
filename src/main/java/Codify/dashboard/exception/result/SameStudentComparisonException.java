package Codify.dashboard.exception.result;

import Codify.dashboard.exception.ErrorCode;
import Codify.dashboard.exception.baseException.BaseException;

public class SameStudentComparisonException extends BaseException {
    public SameStudentComparisonException() {
        super(ErrorCode.SAME_STUDENT_COMPARISON);
    }
}

