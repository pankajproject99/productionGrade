package com.example.productionGrade.dto;

import com.example.productionGrade.entity.Task;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TaskMapper {

    public TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted());
    }

    public Task toEntity(CreateTaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        return task;
    }
}
