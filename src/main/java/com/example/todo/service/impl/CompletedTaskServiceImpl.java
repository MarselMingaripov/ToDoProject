package com.example.todo.service.impl;

import com.example.todo.exception.UserDontHasTaskException;
import com.example.todo.exception.UserNotFoundException;
import com.example.todo.model.CompletedTask;
import com.example.todo.model.Task;
import com.example.todo.repository.CompletedTaskRepository;
import com.example.todo.repository.TaskRepository;
import com.example.todo.security.model.User;
import com.example.todo.security.repository.UserRepository;
import com.example.todo.service.CompletedTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompletedTaskServiceImpl implements CompletedTaskService {
    private final CompletedTaskRepository completedTaskRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public CompletedTask markAsCompleted(String username, String taskname) throws UserNotFoundException, UserDontHasTaskException {
        if (userRepository.existsByUsername(username)) {
            User user = userRepository.findUserByUsername(username);
            if (user.getTasks().contains(taskRepository.findByName(taskname).get())){
                Task task = taskRepository.findByName(taskname).get();
                CompletedTask completedTask = new CompletedTask(task.getName(), task.getDescription(), task.getImportance());
                completedTaskRepository.save(completedTask);
                user.getTasks().remove(task);
                user.getCompletedTasks().add(completedTask);
                taskRepository.delete(task);
                return completedTask;
            } else {
                throw new UserDontHasTaskException("User do not has that task!");
            }
        } else {
            throw new UserNotFoundException("User not found!");
        }
    }
}
