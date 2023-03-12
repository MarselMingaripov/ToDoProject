package com.example.todo.service;

import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.exception.UserDontHasTaskException;
import com.example.todo.exception.UserNotFoundException;
import com.example.todo.model.Task;

import java.util.Set;

public interface UserService {

    Task updateTask(Task task, String username, String taskName) throws Exception, TaskNotFoundException, UserNotFoundException, UserDontHasTaskException;

    Set<Task> getAllUserTasks(String username);
}
