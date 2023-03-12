package com.example.todo.service.impl;

import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.exception.TaskValidationException;
import com.example.todo.exception.UserNotFoundException;
import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
import com.example.todo.security.model.User;
import com.example.todo.security.repository.UserRepository;
import com.example.todo.service.TaskService;
import com.example.todo.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ValidationService validationService;

    @Override
    public Task createTask(Task task) throws TaskValidationException {
        if (validationService.validate(task)) {
            return taskRepository.save(task);
        } else {
            throw new TaskValidationException("Task has invalid parametrs!");
        }
    }

    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    @Override
    public User addExecutor(String taskname, String username) throws TaskNotFoundException, UserNotFoundException {
        if (taskRepository.existsByName(taskname)) {
            if (userRepository.existsByUsername(username)) {
                User user = userRepository.findUserByUsername(username);
                user.getTasks().add(taskRepository.findByName(taskname).get());
                return userRepository.save(user);
            } else {
                throw new UserNotFoundException("User not found!");
            }
        } else throw new TaskNotFoundException("Task is not saved!");

    }

    @Override
    public boolean deleteTask(String name) throws TaskNotFoundException {
        if (taskRepository.existsByName(name)) {
            taskRepository.delete(taskRepository.findByName(name).get());
            return true;
        } else {
            throw new TaskNotFoundException("Task not found!");
        }
    }
}
