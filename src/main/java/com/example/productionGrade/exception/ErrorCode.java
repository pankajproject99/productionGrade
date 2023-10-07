package com.example.productionGrade.exception;

// With Code, name and description
public enum ErrorCode {
    TASK_SERVICE_ERROR,
    TASK_NOT_FOUND,
    NOT_FOUND,
    BAD_REQUEST,
    //Name, ErrorCode and Description
    CONFLICT("Conflict",409,"Duplicate Data"),
    UNEXPECTED_ERROR;

    private final Object[] values;

    ErrorCode(Object...vals){
        values = vals;
    }

    public String shortName(){
        return (String) values[0];
    }

    public String code(){
        return (String) values[1];
    }

    public String description(){
        return (String) values[2];
    }
}
