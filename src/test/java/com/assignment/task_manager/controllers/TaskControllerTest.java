package com.assignment.task_manager.controllers;

import com.assignment.task_manager.AbstractTest;
import com.assignment.task_manager.advice.ResponsePayload;
import com.assignment.task_manager.dto.TaskDTO;
import com.assignment.task_manager.dto.enums.TaskPriority;
import com.assignment.task_manager.dto.enums.TaskStatus;
import com.assignment.task_manager.entity.Task;
import com.assignment.task_manager.entity.User;
import com.assignment.task_manager.repo.TaskRepository;
import com.assignment.task_manager.repo.UserRepository;
import com.assignment.task_manager.utility.TokenDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TaskControllerTest extends AbstractTest {
    @Autowired
    private MockMvc mvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TokenDetails tokenDetails;


    final String token = "";

    @Test
    void tasCreate() throws Exception {
        String url = "/api/v1/task/create";
        String email = "test@example.com";
        String userId = "12345";
        TaskDTO taskDTO = new TaskDTO();
        Task task = new Task();

        // Initialize TaskDTO and Task
        taskDTO.setId(2L);
        taskDTO.setTitle("title");
        taskDTO.setDescription("asdsad");
        taskDTO.setStatus(TaskStatus.PENDING);
        taskDTO.setUserId("32cedb0a-eb87-4110-9182-240ddc0d3f67");
        taskDTO.setPriority(TaskPriority.HIGH);

        task.setId(2L);
        task.setTitle("title");
        task.setDescription("adsdsd");
        task.setStatus(TaskStatus.PENDING);
        task.setUser(new User("32cedb0a-eb87-4110-9182-240ddc0d3f67", "navod@gmail.com"));
        task.setPriority(TaskPriority.HIGH);

        // Mock external dependencies behavior
        when(tokenDetails.getEmail()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(new User(userId, email));
        when(modelMapper.map(taskDTO, Task.class)).thenReturn(task);
        String inputJson = super.mapToJson(taskDTO);
        when(taskRepository.save(task)).thenReturn(task);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson)
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        ResponsePayload responsePayload = mapper.readValue(mvcResult.getResponse().getContentAsString(), ResponsePayload.class);

        assertEquals(HttpStatus.OK.value(), responsePayload.getStatus().value());
        assertEquals("Task Created", responsePayload.getData());
    }

    @Test
    void updateTask() throws Exception {
        String url = "/api/v1/task/update";
        String email = "test@example.com";
        String userId = "12345";
        TaskDTO taskDTO = new TaskDTO();
        Task task = new Task();


        taskDTO.setId(2L);
        taskDTO.setTitle("title updated");
        taskDTO.setDescription("updated description");
        taskDTO.setStatus(TaskStatus.DONE);
        taskDTO.setUserId(userId);
        taskDTO.setPriority(TaskPriority.MEDIUM);

        task.setId(2L);
        task.setTitle("title updated");
        task.setDescription("updated description");
        task.setStatus(TaskStatus.DONE);
        task.setUser(new User(userId, email));
        task.setPriority(TaskPriority.MEDIUM);


        when(tokenDetails.getEmail()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(new User(userId, email));
        when(modelMapper.map(taskDTO, Task.class)).thenReturn(task);
        String inputJson = super.mapToJson(taskDTO);
        when(taskRepository.save(task)).thenReturn(task);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson)
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        ResponsePayload responsePayload = mapper.readValue(mvcResult.getResponse().getContentAsString(), ResponsePayload.class);

        assertEquals(HttpStatus.OK.value(), responsePayload.getStatus().value());
        assertEquals("Task updated", responsePayload.getData());
    }

    @Test
    void deleteTask() throws Exception {
        Long taskId = 2L;
        Task task = new Task();
        task.setId(taskId);
        task.setTitle("title to delete");
        task.setDescription("description to delete");

        when(taskRepository.findById(taskId)).thenReturn(java.util.Optional.of(task));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete("/api/v1/task/delete")
                        .param("id", String.valueOf(taskId))
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        ResponsePayload responsePayload = mapper.readValue(mvcResult.getResponse().getContentAsString(), ResponsePayload.class);

        assertEquals(HttpStatus.OK.value(), responsePayload.getStatus().value());
        assertEquals("Task Deleted", responsePayload.getData());
    }
}
