package com.assignment.task_manager.dto;

import com.assignment.task_manager.dto.enums.TaskPriority;
import com.assignment.task_manager.dto.enums.TaskStatus;
import com.assignment.task_manager.entity.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;

    @NotNull(message = "title cannot be empty")
    @NotEmpty(message = "title cannot be empty")
    @NotBlank(message = "title cannot be empty")
    private String title;

    @NotNull(message = "description cannot be empty")
    @NotEmpty(message = "description cannot be empty")
    @NotBlank(message = "description cannot be empty")
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    private String dueDate;
    private String userId;

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.dueDate = task.getDueDate();
        this.priority = task.getPriority();
        this.status = task.getStatus();
    }
}
