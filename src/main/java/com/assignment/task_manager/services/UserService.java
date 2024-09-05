package com.assignment.task_manager.services;

import com.assignment.task_manager.advice.ResponsePayload;
import com.assignment.task_manager.dto.UserDTO;

public interface UserService {

    ResponsePayload createUser(UserDTO user);
}
