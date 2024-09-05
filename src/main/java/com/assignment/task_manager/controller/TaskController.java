package com.assignment.task_manager.controller;

import com.assignment.task_manager.advice.ResponsePayload;
import com.assignment.task_manager.dto.TaskDTO;
import com.assignment.task_manager.dto.UserDTO;
import com.assignment.task_manager.services.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task")
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<ResponsePayload> createTask(@RequestBody TaskDTO taskDTO) {
        log.info("task create controller called : task | {} ", taskDTO);
        return ResponseEntity.status(HttpStatus.OK).body(taskService.createTask(taskDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponsePayload> updateTask(@RequestBody TaskDTO taskDTO) {
        log.info("task update controller called : task | {} ", taskDTO);
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(taskDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponsePayload> deleteTask(@RequestParam Long id) {
        log.info("task delete controller called : task | {} ", id);
        return ResponseEntity.status(HttpStatus.OK).body(taskService.deleteTask(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ResponsePayload> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "priority") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTask(pageable));
    }
}
