package com.example.todo.service.impl;

import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.exception.UserDontHasTaskException;
import com.example.todo.exception.UserNotFoundException;
import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
import com.example.todo.security.model.User;
import com.example.todo.security.repository.UserRepository;
import com.example.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    @Override
    public Task updateTask(Task task, String username, String taskName) throws TaskNotFoundException, UserNotFoundException, UserDontHasTaskException {
        if (userRepository.existsByUsername(username)){
            User user = userRepository.findUserByUsername(username);
            if (!user.getTasks().isEmpty()){
                Task updatingTask = taskRepository.findByName(taskName).get();
                Optional<Task> task1 = user.getTasks().stream()
                        .filter(x -> x.getName().equals(taskName))
                        .peek(x -> x.setDescription(task.getDescription()))
                        .peek(x -> x.setImportance(task.getImportance()))
                        .peek(x -> x.setName(task.getName()))
                        .findFirst();
                if (task1.isPresent()){
                    taskRepository.save(task1.get());
                    user.getTasks().add(task1.get());
                    user.getTasks().remove(updatingTask);
                    return task1.get();
                } else {
                    throw new TaskNotFoundException("Task at users task list not found!");
                }
            } else {
                throw new UserDontHasTaskException("User dont has task with name: " + taskName);
            }
        } else {
            throw new UserNotFoundException("User not found!");
        }
    }

    @Override
    public Set<Task> getAllUserTasks(String username){
        return userRepository.findUserByUsername(username).getTasks();
    }
}
