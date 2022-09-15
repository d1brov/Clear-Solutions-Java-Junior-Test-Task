package com.clearsolutions.testassignment.web.controller;

import com.clearsolutions.testassignment.service.UsersService;
import com.clearsolutions.testassignment.web.exception.GlobalExceptionHandler;
import com.clearsolutions.testassignment.web.util.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    protected MockMvc mvc;

    @Mock
    protected UsersService mockService;

    protected UserMapper userMapper;
    protected ObjectMapper jsonMapper;

    public UserControllerTest() {
        jsonMapper = new ObjectMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .standaloneSetup(new UsersController(mockService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

}