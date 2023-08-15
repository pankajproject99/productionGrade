package com.example.productionGrade.service;

import com.example.productionGrade.Dto.CreateTaskRequest;
import com.example.productionGrade.Dto.TaskResponse;

import java.util.List;

public interface TaskService {
    List<TaskResponse> getAllTasks();
    TaskResponse createTask(CreateTaskRequest request);
    TaskResponse markTaskAsCompleted(Long id);
}
