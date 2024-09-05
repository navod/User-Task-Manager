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
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponsePayload> register(@RequestBody UserDTO request) {
        log.info("user register details : RegisterRequest | {} ", request.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(request));
    }
}
