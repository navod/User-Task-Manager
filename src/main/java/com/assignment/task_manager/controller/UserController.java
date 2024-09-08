package com.assignment.task_manager.controller;

import com.assignment.task_manager.advice.ResponsePayload;
import com.assignment.task_manager.dto.UserDTO;
import com.assignment.task_manager.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponsePayload> register(@RequestBody UserDTO request) {
        log.info("user register details : RegisterRequest | {} ", request.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(request));
    }

    /**
     * Handles the user signup process by accepting user details via a POST request.
     * This method is invoked when a user signs up through OAuth0. It logs the user's email
     * and returns a success response upon receiving the user data.
     *
     * @param userDto The user details sent in the request body, encapsulated in a {@link UserDTO} object.
     * @return A {@link ResponseEntity} containing a success message confirming that the user data
     * has been received.
     */
    @PostMapping("/signup")
    public ResponseEntity<ResponsePayload> handleUserSignup(@RequestBody UserDTO userDto) {
        log.info("user register details : signup | {} ", userDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(userDto));
    }
}
