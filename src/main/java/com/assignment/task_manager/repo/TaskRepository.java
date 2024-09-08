package com.assignment.task_manager.repo;

import com.assignment.task_manager.dto.enums.TaskStatus;
import com.assignment.task_manager.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByUser_UserId(String id, Pageable pageable);

    Page<Task> findByUser_UserIdAndStatusIn(String id, List<TaskStatus> statuses, Pageable pageable);

}
