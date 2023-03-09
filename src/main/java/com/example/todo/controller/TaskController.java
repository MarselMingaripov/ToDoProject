package com.example.todo.controller;

import com.example.todo.model.Task;
import com.example.todo.security.model.User;
import com.example.todo.service.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Tag(name = "Task API")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        return ResponseEntity.ok().body(taskService.createTask(task));
    }

    @GetMapping("/all")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<Task>> getAllTasks(){
        return ResponseEntity.ok().body(taskService.getAllTask());
    }

    @PostMapping("/setExecutor/{username}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<User> setExecutor(@RequestBody Task task, @RequestParam String username) throws Exception {
        return ResponseEntity.ok().body(taskService.addExecutor(task, username));
    }
}
