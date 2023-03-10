package com.example.todo.service;

import com.example.todo.model.Task;
import com.example.todo.security.model.User;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);

    List<Task> getAllTask();

    User addExecutor(String taskname, String username) throws Exception;

    void deleteTask(String name);
}
