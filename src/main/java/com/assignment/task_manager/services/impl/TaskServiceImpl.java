package com.assignment.task_manager.services.impl;

import com.assignment.task_manager.advice.ResponsePayload;
import com.assignment.task_manager.dto.TaskDTO;
import com.assignment.task_manager.entity.Task;
import com.assignment.task_manager.entity.User;
import com.assignment.task_manager.repo.TaskRepository;
import com.assignment.task_manager.repo.UserRepository;
import com.assignment.task_manager.services.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;

    @Override
    public ResponsePayload createTask(TaskDTO taskDTO) {
        Task task = modelMapper.map(taskDTO, Task.class);
        taskRepository.save(task);

        return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "Task Created", HttpStatus.OK);
    }

    @Override
    public ResponsePayload updateTask(TaskDTO taskDTO) {
        Task task = modelMapper.map(taskDTO, Task.class);
        taskRepository.save(task);

        return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "Task updated", HttpStatus.OK);
    }

    @Override
    public ResponsePayload deleteTask(Long id) {
        Task task = taskRepository.findById(id).get();
        taskRepository.delete(task);
        return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "Task Deleted", HttpStatus.OK);
    }

    @Override
    public ResponsePayload getAllTask(Pageable pageable) {
        String userId = "32cedb0a-eb87-4110-9182-240ddc0d3f67";
        Page<Task> allTasks = taskRepository.findByUser_Id(userId, pageable);

        List<TaskDTO> taskDTOs = allTasks.getContent()
                .stream()
                .map(TaskDTO::new)
                .collect(Collectors.toList());

        Page<TaskDTO> taskDTOPage = new PageImpl<>(taskDTOs, pageable, allTasks.getTotalElements());

        return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), taskDTOPage, HttpStatus.OK);
    }
}
