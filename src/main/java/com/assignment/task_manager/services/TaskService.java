package com.assignment.task_manager.services;

import com.assignment.task_manager.advice.ResponsePayload;
import com.assignment.task_manager.dto.TaskDTO;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    ResponsePayload createTask(TaskDTO taskDTO);

    ResponsePayload updateTask(TaskDTO taskDTO);

    ResponsePayload deleteTask(Long id);

    ResponsePayload getAllTask(Pageable pageable, String status);
}
