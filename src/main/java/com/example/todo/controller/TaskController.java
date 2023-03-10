package com.example.todo.controller;

import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.exception.TaskValidationException;
import com.example.todo.exception.UserDontHasTaskException;
import com.example.todo.exception.UserNotFoundException;
import com.example.todo.model.CompletedTask;
import com.example.todo.model.Task;
import com.example.todo.security.model.User;
import com.example.todo.service.CompletedTaskService;
import com.example.todo.service.TaskService;
import com.example.todo.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final CompletedTaskService completedTaskService;

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Task> createTask(@RequestBody Task task) throws TaskValidationException {
        return ResponseEntity.ok().body(taskService.createTask(task));
    }

    @GetMapping("/all")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MOD') or hasRole('USER')")
    public ResponseEntity<List<Task>> getAllTasks(){
        return ResponseEntity.ok().body(taskService.getAllTask());
    }

    @PostMapping("/setExecutor/{username}/{taskname}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> setExecutor(@RequestParam String taskname, @RequestParam String username) throws UserNotFoundException, TaskNotFoundException {
        return ResponseEntity.ok().body(taskService.addExecutor(taskname, username));
    }

    @PutMapping("/{username}/{taskName}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MOD')")
    public ResponseEntity<Task> updateTaskInUserTaskList(@RequestBody Task task, @RequestParam String username, @RequestParam String taskName) throws Exception {
        return ResponseEntity.ok().body(userService.updateTask(task, username, taskName));
    }

    @GetMapping("usersTasks/{username}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MOD')")
    public ResponseEntity<Set<Task>> getAllUsersTasks(@RequestParam String username){
        return ResponseEntity.ok().body(userService.getAllUserTasks(username));
    }

    @GetMapping("userTasks")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MOD') or hasRole('USER')")
    public ResponseEntity<Set<Task>> getAllUserTasks(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(userService.getAllUserTasks(authentication.getName()));
    }

    @PostMapping("markAsComplited/{username}/{taskname}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<CompletedTask> markTaskAsComplited(@RequestParam String username, @RequestParam String taskname) throws UserNotFoundException, UserDontHasTaskException {
        return ResponseEntity.ok().body(completedTaskService.markAsCompleted(username, taskname));
    }

    @DeleteMapping("{taskname}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MOD')")
    public ResponseEntity<Void> deleteTask(@RequestParam String taskname) throws TaskNotFoundException {
        taskService.deleteTask(taskname);
        return ResponseEntity.ok().build();
    }

}
