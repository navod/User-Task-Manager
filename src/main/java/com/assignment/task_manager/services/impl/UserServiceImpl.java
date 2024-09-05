package com.assignment.task_manager.services.impl;

import com.assignment.task_manager.advice.ResponsePayload;
import com.assignment.task_manager.dto.UserDTO;
import com.assignment.task_manager.entity.User;
import com.assignment.task_manager.repo.UserRepository;
import com.assignment.task_manager.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public ResponsePayload createUser(UserDTO user) {
        User userEntity = modelMapper.map(user, User.class);
        userEntity.setId(UUID.randomUUID().toString());
        userRepository.save(userEntity);

        return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "User Added", HttpStatus.OK);
    }
}
