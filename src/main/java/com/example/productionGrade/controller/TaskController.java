package com.example.productionGrade.controller;

import com.example.productionGrade.Dto.CreateTaskRequest;
import com.example.productionGrade.Dto.TaskResponse;
import com.example.productionGrade.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return taskService.getAllTasks();
    }

    /**
     * Create a new task
     * @param request The CreateTaskRequest object containing task details
     * @return The created TaskResponse object
     */
    @PostMapping("/create")
    public TaskResponse createTask(@RequestBody CreateTaskRequest request) {
        return taskService.createTask(request);
    }

    /**
     * Mark a task as completed
     * @param id The ID of the task to be marked as completed
     * @return ResponseEntity containing the updated TaskResponse object
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> markTaskAsCompleted(@PathVariable("id") Long id) {
        return ResponseEntity.ok(taskService.markTaskAsCompleted(id));
    }
}

