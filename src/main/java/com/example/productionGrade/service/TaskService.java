package com.example.productionGrade.service;

import com.example.productionGrade.dto.CreateTaskRequest;
import com.example.productionGrade.dto.TaskResponse;

import java.util.List;

public interface TaskService {
    List<TaskResponse> getAllTasks();
    TaskResponse createTask(CreateTaskRequest request);
    TaskResponse markTaskAsCompleted(Long id);
}
