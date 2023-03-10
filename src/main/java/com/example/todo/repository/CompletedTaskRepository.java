package com.example.todo.repository;

import com.example.todo.model.CompletedTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletedTaskRepository extends JpaRepository<CompletedTask, Long> {
}
