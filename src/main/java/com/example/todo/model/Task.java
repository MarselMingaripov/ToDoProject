package com.example.todo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tasks",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
@Data
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    private String name;

    @NotBlank
    @Size(max = 30)
    private String description;

    private Importance importance;

    public Task(String name, String description, Importance importance) {
        this.name = name;
        this.description = description;
        this.importance = importance;
    }
}
