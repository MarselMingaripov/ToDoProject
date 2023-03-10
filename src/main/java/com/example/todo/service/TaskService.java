package com.example.todo.service;

import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.exception.TaskValidationException;
import com.example.todo.exception.UserNotFoundException;
import com.example.todo.model.Task;
import com.example.todo.security.model.User;

import java.util.List;

public interface TaskService {
    Task createTask(Task task) throws TaskValidationException;

    List<Task> getAllTask();

    User addExecutor(String taskname, String username) throws TaskNotFoundException, UserNotFoundException;

    boolean deleteTask(String name) throws TaskNotFoundException;


}
