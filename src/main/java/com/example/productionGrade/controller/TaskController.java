package com.example.productionGrade.controller;

import com.example.productionGrade.Dto.CreateTaskRequest;
import com.example.productionGrade.Dto.TaskResponse;
import com.example.productionGrade.exception.BadRequestException;
import com.example.productionGrade.exception.NotFoundException;
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
                throw new NotFoundException("Task with ID " + id + " not found.");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException("Error marking task as completed: " + e.getMessage());
        }
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}