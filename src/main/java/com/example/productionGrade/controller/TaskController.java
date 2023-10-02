package com.example.productionGrade.controller;

import com.example.productionGrade.dto.CreateTaskRequest;
import com.example.productionGrade.dto.TaskResponse;
import com.example.productionGrade.exception.BadRequestException;
import com.example.productionGrade.exception.ErrorCode;
import com.example.productionGrade.exception.TaskNotFoundException;
import com.example.productionGrade.exception.TaskServiceException;
import com.example.productionGrade.service.TaskService;
import com.example.productionGrade.validate.Validate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Get all tasks
     * @return List of TaskResponse objects
     */
    @GetMapping
    public List<TaskResponse> getAllTasks() {
        try {
            return taskService.getAllTasks();
        } catch (TaskServiceException e) {
            throw new TaskServiceException(ErrorCode.TASK_SERVICE_ERROR, "Error retrieving tasks: " + e.getMessage());
        }
    }

    /**
     * Create a new task
     * @param request The CreateTaskRequest object containing task details
     * @return The created TaskResponse object
     */
    @PostMapping("/create")
    @Validate
    public TaskResponse createTask(@Valid @RequestBody CreateTaskRequest request) {
        try {
            return taskService.createTask(request);
        } catch (TaskServiceException e) {
            throw new BadRequestException("Invalid request: " + e.getMessage());
        }
    }

    /**
     * Mark a task as completed
     * @param id The ID of the task to be marked as completed
     * @return ResponseEntity containing the updated TaskResponse object
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> markTaskAsCompleted(@PathVariable("id") Long id) {
        try {
            TaskResponse response = taskService.markTaskAsCompleted(id);
            if (response == null) {
                throw new TaskNotFoundException("Task with ID " + id + " not found.");
            }
            return ResponseEntity.ok(response);
        } catch (TaskServiceException e) {
            throw new TaskServiceException(ErrorCode.TASK_SERVICE_ERROR, "Error marking task as completed: " + e.getMessage());
        }
    }
}