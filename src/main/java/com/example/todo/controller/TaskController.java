package com.example.todo.controller;

import com.example.todo.model.Task;
import com.example.todo.security.model.User;
import com.example.todo.service.TaskService;
import com.example.todo.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Tag(name = "Task API")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

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

    @PostMapping("/setExecutor/{username}/{taskname}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<User> setExecutor(@RequestParam String taskname, @RequestParam String username) throws Exception {
        return ResponseEntity.ok().body(taskService.addExecutor(taskname, username));
    }

    @PutMapping("/{username}/{taskName}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Task> updateTaskInUserTaskList(@RequestBody Task task, @RequestParam String username, @RequestParam String taskName) throws Exception {
        return ResponseEntity.ok().body(userService.updateTask(task, username, taskName));
    }

    @GetMapping("usersTasks/{username}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Set<Task>> getAllUsersTasks(@RequestParam String username){
        return ResponseEntity.ok().body(userService.getAllUserTasks(username));
    }

}
