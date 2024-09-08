package com.assignment.task_manager.services.impl;

import com.assignment.task_manager.advice.ResponsePayload;
import com.assignment.task_manager.dto.TaskDTO;
import com.assignment.task_manager.dto.enums.TaskStatus;
import com.assignment.task_manager.entity.Task;
import com.assignment.task_manager.entity.User;
import com.assignment.task_manager.repo.TaskRepository;
import com.assignment.task_manager.repo.UserRepository;
import com.assignment.task_manager.services.TaskService;
import com.assignment.task_manager.utility.TokenDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public ResponsePayload createTask(TaskDTO taskDTO) {
        String email = TokenDetails.getEmail();
        String userId = userRepository.findByEmail(email).getUserId();
        taskDTO.setUserId(userId);

        Task task = modelMapper.map(taskDTO, Task.class);
        taskRepository.save(task);

        return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "Task Created", HttpStatus.OK);
    }

    @Override
    public ResponsePayload updateTask(TaskDTO taskDTO) {
        String email = TokenDetails.getEmail();
        String userId = userRepository.findByEmail(email).getUserId();
        taskDTO.setUserId(userId);

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
    public ResponsePayload getAllTask(Pageable pageable, String statusFilter) {
        String email = TokenDetails.getEmail();
        String userId = userRepository.findByEmail(email).getUserId();

        Page<Task> allTasks;

        if ("All".equalsIgnoreCase(statusFilter)) {
            // If "All", retrieve tasks without filtering by status
            allTasks = taskRepository.findByUser_UserId(userId, pageable);
        } else {
            // Otherwise, filter by the specific status
            TaskStatus status = TaskStatus.valueOf(statusFilter.toUpperCase());
            allTasks = taskRepository.findByUser_UserIdAndStatusIn(userId, List.of(status), pageable);
        }


        List<TaskDTO> taskDTOs = allTasks.getContent()
                .stream()
                .map(TaskDTO::new)
                .collect(Collectors.toList());

        Page<TaskDTO> taskDTOPage = new PageImpl<>(taskDTOs, pageable, allTasks.getTotalElements());

        return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), taskDTOPage, HttpStatus.OK);
    }
}
