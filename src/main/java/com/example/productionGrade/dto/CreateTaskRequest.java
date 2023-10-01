package com.example.productionGrade.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateTaskRequest {
    @NotEmpty
    @Size(max = 100)
    private String title;

    @NotNull
    @Size(max = 500)
    private String description;

    // Getters, setters
}
