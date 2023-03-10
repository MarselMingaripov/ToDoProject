package com.example.todo.service.impl;

import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
import com.example.todo.security.model.User;
import com.example.todo.security.repository.UserRepository;
import com.example.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    @Override
    public Task updateTask(Task task, String username, String taskName) throws Exception {
        if (userRepository.existsByUsername(username)){
            User user = userRepository.findUserByUsername(username);
            if (!user.getTasks().isEmpty()){
                Task updatingTask = taskRepository.findByName(taskName).get();
                Task task1 = user.getTasks().stream()
                        .filter(x -> x.getName().equals(taskName))
                        .peek(x -> x.setDescription(task.getDescription()))
                        .peek(x -> x.setImportance(task.getImportance()))
                        .peek(x -> x.setName(task.getName()))
                        .findFirst().get();
                if (task1 != null){
//                    taskRepository.delete(updatingTask);
                    taskRepository.save(task1);
                    user.getTasks().add(task1);
                    user.getTasks().remove(updatingTask);
                    return task1;
                } else {
                    throw new Exception("Task at users task list not found!");
                }
            } else {
                throw new Exception("Request is incorrect!");
            }
        } else {
            throw new Exception("User not found!");
        }
    }

    @Override
    public Set<Task> getAllUserTasks(String username){
        return userRepository.findUserByUsername(username).getTasks();
    }
}
