package com.example.todo.service.impl;

import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.exception.TaskValidationException;
import com.example.todo.exception.UserNotFoundException;
import com.example.todo.model.Importance;
import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
import com.example.todo.security.model.User;
import com.example.todo.security.repository.UserRepository;
import com.example.todo.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    public static final String USERNAME = "username";
    public static final String TASKNAME = "taskname";
    public static final String DESCRIPTION = "description";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email@mail.email";
    private Task task;
    private User user;
    private List<Task> tasks = new ArrayList<>(List.of(new Task(TASKNAME, DESCRIPTION, Importance.LOW)));

    @BeforeEach
    void init(){
        task = new Task(TASKNAME, DESCRIPTION, Importance.LOW);
        user = new User(USERNAME, PASSWORD, EMAIL);
    }

    @Mock
    private TaskRepository taskRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private ValidationService validationServiceMock;

    @InjectMocks
    private TaskServiceImpl out;

    @Test
    void shouldThrowTaskValidationExceptionWhenTaskDataIsIncorrect() {
        when(validationServiceMock.validate(task)).thenReturn(false);
        assertThrows(TaskValidationException.class, () -> out.createTask(task));
    }

    @Test
    void shouldReturnTaskWhenTaskDataIsCorrect() throws TaskValidationException {
        when(validationServiceMock.validate(task)).thenReturn(true);
        when(taskRepositoryMock.save(any())).thenReturn(task);
        assertEquals(task, out.createTask(task));
    }

    @Test
    void shouldReturnListOfTasks() {
        when(taskRepositoryMock.findAll()).thenReturn(tasks);
        assertEquals(tasks, out.getAllTask());
    }

    @Test
    void shouldThrowTaskNotFoundExceptionWhenTaskNameIsIncorrect() {
        when(taskRepositoryMock.existsByName(TASKNAME)).thenReturn(false);
        assertThrows(TaskNotFoundException.class, () -> out.addExecutor(TASKNAME, USERNAME));
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenExecutorNameIsIncorrect() {
        when(taskRepositoryMock.existsByName(TASKNAME)).thenReturn(true);
        when(userRepositoryMock.existsByUsername(USERNAME)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> out.addExecutor(TASKNAME, USERNAME));
    }

    @Test
    void shouldReturnUserWhenAddExecutor() throws UserNotFoundException, TaskNotFoundException {
        when(taskRepositoryMock.existsByName(TASKNAME)).thenReturn(true);
        when(userRepositoryMock.existsByUsername(USERNAME)).thenReturn(true);
        when(userRepositoryMock.findUserByUsername(USERNAME)).thenReturn(user);
        when(taskRepositoryMock.findByName(TASKNAME)).thenReturn(Optional.of(task));
        when(userRepositoryMock.save(any())).thenReturn(user);
        assertEquals(user, out.addExecutor(TASKNAME, USERNAME));
    }

    @Test
    void shouldThrowTaskNotFoundExceptionWhenTaskIsNotExistsByName() {
        when(taskRepositoryMock.existsByName(TASKNAME)).thenReturn(false);
        assertThrows(TaskNotFoundException.class, () -> out.deleteTask(TASKNAME));
    }

    @Test
    void shouldReturnTrueWhenTaskIsExistsByName() throws TaskNotFoundException {
        when(taskRepositoryMock.existsByName(TASKNAME)).thenReturn(true);
        when(taskRepositoryMock.findByName(TASKNAME)).thenReturn(Optional.of(task));
        assertTrue(out.deleteTask(TASKNAME));
    }
}