package com.example.productionGrade.Dto;

import lombok.Data;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private boolean completed;

    // Constructors, getters, setters
}
