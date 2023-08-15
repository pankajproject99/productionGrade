package com.example.productionGrade.repo;

import com.example.productionGrade.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
