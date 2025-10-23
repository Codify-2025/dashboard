package Codify.dashboard.exception.result;

import Codify.dashboard.exception.ErrorCode;
import Codify.dashboard.exception.baseException.BaseException;

public class ComparisonResultNotFoundException extends BaseException {
    public ComparisonResultNotFoundException() {
        super(ErrorCode.COMPARISON_RESULT_NOT_FOUND);
    }
}