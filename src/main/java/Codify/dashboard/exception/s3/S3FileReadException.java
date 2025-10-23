package Codify.dashboard.exception.s3;

import Codify.dashboard.exception.ErrorCode;
import Codify.dashboard.exception.baseException.BaseException;

public class S3FileReadException extends BaseException {
    public S3FileReadException() {
        super(ErrorCode.S3_FILE_READ_ERROR);
    }
}
