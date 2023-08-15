package com.example.productionGrade.exception;

public class TaskServiceException extends RuntimeException {
    public TaskServiceException(String message) {
        super(message);
    }
}