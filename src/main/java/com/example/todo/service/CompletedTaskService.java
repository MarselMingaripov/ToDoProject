package com.example.todo.service;

import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.exception.UserDontHasTaskException;
import com.example.todo.exception.UserNotFoundException;
import com.example.todo.model.CompletedTask;
import com.example.todo.model.Task;

public interface CompletedTaskService {

    CompletedTask markAsCompleted(String username, String taskname) throws UserNotFoundException, UserDontHasTaskException, TaskNotFoundException;
}
