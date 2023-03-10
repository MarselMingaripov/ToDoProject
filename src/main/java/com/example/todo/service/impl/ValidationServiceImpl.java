package com.example.todo.service.impl;

import com.example.todo.model.Task;
import com.example.todo.service.ValidationService;
import org.apache.commons.lang3.StringUtils;

public class ValidationServiceImpl implements ValidationService {
    @Override
    public boolean validate(Task task) {
        return (StringUtils.isNotBlank(task.getName()) &&
                StringUtils.isNotBlank(task.getDescription()));
    }
}
