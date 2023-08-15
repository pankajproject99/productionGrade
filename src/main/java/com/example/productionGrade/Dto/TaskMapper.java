package com.example.productionGrade.Dto;

import com.example.productionGrade.Entity.Task;
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
