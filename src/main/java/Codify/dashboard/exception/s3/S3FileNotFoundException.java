package Codify.dashboard.exception.s3;

import Codify.dashboard.exception.ErrorCode;
import Codify.dashboard.exception.baseException.BaseException;

public class S3FileNotFoundException extends BaseException {
    public S3FileNotFoundException() {
        super(ErrorCode.S3_FILE_NOT_FOUND);
    }
}

