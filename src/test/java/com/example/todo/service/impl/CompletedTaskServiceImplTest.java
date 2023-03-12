package com.example.todo.service.impl;

import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.exception.UserDontHasTaskException;
import com.example.todo.exception.UserNotFoundException;
import com.example.todo.model.CompletedTask;
import com.example.todo.model.Importance;
import com.example.todo.model.Task;
import com.example.todo.repository.CompletedTaskRepository;
import com.example.todo.repository.TaskRepository;
import com.example.todo.security.model.User;
import com.example.todo.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletedTaskServiceImplTest {

    @Mock
    private CompletedTaskRepository completedTaskRepositoryMock;

    @Mock
    private TaskRepository taskRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private CompletedTaskServiceImpl out;

    public static final String USERNAME = "username";
    public static final String TASKNAME = "taskname";
    public static final String DESCRIPTION = "description";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email@mail.email";
    private Task task;
    private User user;
    private CompletedTask completedTask;
    private List<Task> tasks = new ArrayList<>(List.of(new Task(TASKNAME, DESCRIPTION, Importance.LOW)));

    @BeforeEach
    void init(){
        task = new Task(TASKNAME, DESCRIPTION, Importance.LOW);
        user = new User(USERNAME, PASSWORD, EMAIL);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserIsNotExistsByUsername() {
        when(userRepositoryMock.existsByUsername(USERNAME)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> out.markAsCompleted(USERNAME, TASKNAME));
    }

    @Test
    void shouldThrowTaskNotFoundExceptionWhenTaskIsNotExistsByUsername() {
        when(userRepositoryMock.existsByUsername(USERNAME)).thenReturn(true);
        when(taskRepositoryMock.existsByName(TASKNAME)).thenReturn(false);
        assertThrows(TaskNotFoundException.class, () -> out.markAsCompleted(USERNAME, TASKNAME));
    }

    @Test
    void shouldThrowUserDontHasTaskExceptionWhenUserTaskListNotContainsTaskByName() {
        when(userRepositoryMock.existsByUsername(USERNAME)).thenReturn(true);
        when(taskRepositoryMock.existsByName(TASKNAME)).thenReturn(true);
        when(userRepositoryMock.findUserByUsername(USERNAME)).thenReturn(user);
        when(taskRepositoryMock.findByName(TASKNAME)).thenReturn(Optional.of(task));
        assertThrows(UserDontHasTaskException.class, () -> out.markAsCompleted(USERNAME, TASKNAME));
    }

    @Test
    void shouldReturnCompletedTaskWhenDataIsCorrect() throws UserNotFoundException, TaskNotFoundException, UserDontHasTaskException {
        completedTask = new CompletedTask(TASKNAME, DESCRIPTION, Importance.LOW);
        user.setTasks(new HashSet<>(Set.of(task)));
        when(userRepositoryMock.existsByUsername(USERNAME)).thenReturn(true);
        when(taskRepositoryMock.existsByName(TASKNAME)).thenReturn(true);
        when(userRepositoryMock.findUserByUsername(USERNAME)).thenReturn(user);
        when(taskRepositoryMock.findByName(TASKNAME)).thenReturn(Optional.of(task));
        when(completedTaskRepositoryMock.save(any())).thenReturn(completedTask);
        assertEquals(completedTask, out.markAsCompleted(USERNAME, TASKNAME));
    }

    @Test
    void shouldReturnAllCompletedTasks(){
        List<CompletedTask> tasks = new ArrayList<>(List.of(new CompletedTask(TASKNAME, DESCRIPTION, Importance.LOW)));
        when(completedTaskRepositoryMock.findAll()).thenReturn(tasks);
        assertEquals(tasks, out.showAllCompletedTasks());
    }
}