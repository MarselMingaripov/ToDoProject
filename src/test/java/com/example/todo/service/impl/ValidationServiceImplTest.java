package com.example.todo.service.impl;

import com.example.todo.model.Importance;
import com.example.todo.model.Task;
import com.example.todo.service.ValidationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationServiceImplTest {

    public static final String CORRECT_NAME = "correctName";
    public static final String CORRECT_DESCRIPTION = "correctDescription";
    public static final Importance importance = Importance.LOW;

    private ValidationService out = new ValidationServiceImpl();

    @Test
    void returnTrueWhenDataIsCorrect() {
        Task task = new Task(CORRECT_NAME, CORRECT_DESCRIPTION, importance);
        assertTrue(out.validate(task));
    }

    @Test
    void returnFalseWhenDataIsIncorrect() {
        Task task = new Task("", "", importance);
        assertFalse(out.validate(task));
    }
}