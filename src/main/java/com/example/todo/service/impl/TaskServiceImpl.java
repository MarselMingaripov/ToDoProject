package com.example.todo.service.impl;

import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
import com.example.todo.security.model.User;
import com.example.todo.security.repository.UserRepository;
import com.example.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public Task createTask(Task task){
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTask(){
        return taskRepository.findAll();
    }

    @Override
    public User addExecutor(Task task, String username) throws Exception {
        if (userRepository.existsByUsername(username)){
            User user1 = userRepository.findUserByUsername(username);
            user1.getTasks().add(task);
            return userRepository.save(user1);
        } else {
            throw new Exception("Bad request!");
        }

    }

    @Override
    public void deleteTask(String name){
        taskRepository.delete(taskRepository.findByName(name).get());
    }
}
