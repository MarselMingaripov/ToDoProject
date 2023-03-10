package com.example.todo.service;

import com.example.todo.model.Task;

import java.util.Set;

public interface UserService {

    Task updateTask(Task task, String username, String taskName) throws Exception;

    Set<Task> getAllUserTasks(String username);
}
