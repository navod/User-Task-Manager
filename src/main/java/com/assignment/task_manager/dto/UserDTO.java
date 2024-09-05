package com.assignment.task_manager.dto;

import com.assignment.task_manager.dto.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    @NotNull
    @NotEmpty
    @NotBlank
    private String firstName;
    private String lastName;
    @NotNull
    @NotEmpty
    @NotBlank
    @Email
    private String email;

    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;
    private boolean status;
    @Enumerated(EnumType.STRING)
    private Role role;
}
