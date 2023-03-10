package com.example.todo.controller;

import com.example.todo.model.CompletedTask;
import com.example.todo.service.CompletedTaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/completedTask")
@RequiredArgsConstructor
@Tag(name = "CompletedTask API")
public class CompletedTaskController {

    private final CompletedTaskService completedTaskService;

    @GetMapping("/all")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MOD')")
    public ResponseEntity<List<CompletedTask>> showAllCompletedTask(){
        return ResponseEntity.ok().body(completedTaskService.showAllCompletedTasks());
    }
}
