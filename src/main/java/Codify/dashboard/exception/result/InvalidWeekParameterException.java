package Codify.dashboard.exception.result;

import Codify.dashboard.exception.ErrorCode;
import Codify.dashboard.exception.baseException.BaseException;

public class InvalidWeekParameterException extends BaseException {
    public InvalidWeekParameterException() {
        super(ErrorCode.INVALID_WEEK_PARAMETER);
    }
}

