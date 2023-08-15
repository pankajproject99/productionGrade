package com.example.productionGrade.service;

import com.example.productionGrade.Dto.CreateTaskRequest;
import com.example.productionGrade.Dto.TaskMapper;
import com.example.productionGrade.Dto.TaskResponse;
import com.example.productionGrade.Entity.Task;
import com.example.productionGrade.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(taskMapper::toTaskResponse).collect(Collectors.toList());
    }

    @Override
    public TaskResponse createTask(CreateTaskRequest request) {
        Task task = taskMapper.toEntity(request);
        task = taskRepository.save(task);
        return taskMapper.toTaskResponse(task);
    }

    @Override
    public TaskResponse markTaskAsCompleted(Long id) {
        Task completedTask = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found"));

        completedTask.setCompleted(true);
        completedTask = taskRepository.save(completedTask);

        return taskMapper.toTaskResponse(completedTask);
    }
}
