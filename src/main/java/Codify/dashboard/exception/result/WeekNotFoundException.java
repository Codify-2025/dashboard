package Codify.dashboard.exception.result;

import Codify.dashboard.exception.ErrorCode;
import Codify.dashboard.exception.baseException.BaseException;

public class WeekNotFoundException extends BaseException {
    public WeekNotFoundException() {
        super(ErrorCode.WEEK_NOT_FOUND);
    }
}