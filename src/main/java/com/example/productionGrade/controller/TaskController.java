package com.example.productionGrade.controller;

import com.example.productionGrade.Dto.CreateTaskRequest;
import com.example.productionGrade.Dto.TaskResponse;
import com.example.productionGrade.service.TaskService;
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
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving tasks: " + e.getMessage());
        }
    }

    /**
     * Create a new task
     * @param request The CreateTaskRequest object containing task details
     * @return The created TaskResponse object
     */
    @PostMapping("/create")
    public TaskResponse createTask(@RequestBody CreateTaskRequest request) {
        try {
            return taskService.createTask(request);
        } catch (Exception e) {
            throw new RuntimeException("Error creating task: " + e.getMessage());
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
            return ResponseEntity.ok(taskService.markTaskAsCompleted(id));
        } catch (Exception e) {
            throw new RuntimeException("Error marking task as completed: " + e.getMessage());
        }
    }

    // Global exception handler
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}