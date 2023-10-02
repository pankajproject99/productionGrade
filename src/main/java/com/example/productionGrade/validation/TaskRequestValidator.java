package com.example.productionGrade.validation;

import com.example.productionGrade.dto.CreateTaskRequest;
import com.example.productionGrade.exception.BadRequestException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Application level validation
 */
@Component
public class TaskRequestValidator {
private final Validator validator;

    public TaskRequestValidator(Validator validator) {
        this.validator = validator;
    }

    public void validate(CreateTaskRequest request) {
        Set<ConstraintViolation<CreateTaskRequest>> violations = validator.validate(request);
        if(!violations.isEmpty()){
            throw new BadRequestException("Invalid request: " + violations.iterator().next().getMessage());
        }
        if(request.getTitle().contains("title")){
            throw new BadRequestException("Title should not contain word title");
        }
    }
}
