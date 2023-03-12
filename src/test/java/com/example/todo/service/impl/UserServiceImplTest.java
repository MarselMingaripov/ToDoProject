package com.example.todo.service.impl;

import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.exception.UserDontHasTaskException;
import com.example.todo.exception.UserNotFoundException;
import com.example.todo.model.Importance;
import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
import com.example.todo.security.model.User;
import com.example.todo.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    public static final String USERNAME = "username";
    public static final String TASKNAME = "taskname";
    public static final String TASKNAME_1 = "taskname1";
    public static final String DESCRIPTION = "description";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email@mail.email";
    private Task task;
    private User user;

    @Mock
    private TaskRepository taskRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private UserServiceImpl out;

    @BeforeEach
    void init(){
        task = new Task(TASKNAME, DESCRIPTION, Importance.LOW);
        user = new User(USERNAME, PASSWORD, EMAIL);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserNotExistsByUsername() {
        when(userRepositoryMock.existsByUsername(anyString())).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> out.updateTask(task, USERNAME, TASKNAME));
    }

    @Test
    void shouldThrowUserDontHasTaskExceptionWhenUserDontHasTask() {
        when(userRepositoryMock.existsByUsername(anyString())).thenReturn(true);
        when(userRepositoryMock.findUserByUsername(USERNAME)).thenReturn(user);
        assertThrows(UserDontHasTaskException.class, () -> out.updateTask(task, USERNAME, TASKNAME));
    }

    @Test
    void shouldThrowTaskNotFoundExceptionWhenUserDontHasTaskWithTaskname() {
        when(userRepositoryMock.existsByUsername(anyString())).thenReturn(true);
        when(userRepositoryMock.findUserByUsername(USERNAME)).thenReturn(user);
        user.setTasks(new HashSet<>(Set.of(new Task(TASKNAME_1, DESCRIPTION, Importance.LOW))));
        when(taskRepositoryMock.findByName(TASKNAME)).thenReturn(Optional.of(task));
        assertThrows(TaskNotFoundException.class, () -> out.updateTask(task, USERNAME, TASKNAME));
    }

    @Test
    void shouldReturnTaskWhenDataIsCorrect() throws UserNotFoundException, TaskNotFoundException, UserDontHasTaskException {
        when(userRepositoryMock.existsByUsername(USERNAME)).thenReturn(true);
        when(userRepositoryMock.findUserByUsername(USERNAME)).thenReturn(user);
        user.setTasks(new HashSet<>(Set.of(task)));
        when(taskRepositoryMock.findByName(TASKNAME)).thenReturn(Optional.of(task));
        when(taskRepositoryMock.save(any())).thenReturn(task);
        assertEquals(task, out.updateTask(task, USERNAME, TASKNAME));
    }

    @Test
    void shouldReturnAllUserTasks() {
        when(userRepositoryMock.findUserByUsername(USERNAME)).thenReturn(user);
        Set<Task> tasks = new HashSet<>(Set.of(task));
        user.setTasks(tasks);
        assertEquals(tasks, out.getAllUserTasks(USERNAME));
    }
}