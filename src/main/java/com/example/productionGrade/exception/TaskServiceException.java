package com.example.productionGrade.exception;

public class TaskServiceException extends RuntimeException {
    private final ErrorCode errorCode;

    public TaskServiceException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}