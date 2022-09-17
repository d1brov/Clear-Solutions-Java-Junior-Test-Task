package com.clearsolutions.testassignment.web.controller;

import com.clearsolutions.testassignment.persistence.repository.UserRepository;
import com.clearsolutions.testassignment.validation.BirthdateValidator;
import com.clearsolutions.testassignment.validation.configuration.ValidationConfiguration;
import com.clearsolutions.testassignment.web.util.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UsersController.class)
@Import({ValidationConfiguration.class, BirthdateValidator.class}) // needed to load external properties for validation
public class UserControllerTest {

    @MockBean
    protected UserRepository mockedUserRepository;

    @Autowired
    protected MockMvc mockMvc;

    protected UserMapper userMapper;
    protected ObjectMapper jsonMapper;
    protected final String baseUrl = "/users";

    public UserControllerTest() {
        userMapper = Mappers.getMapper(UserMapper.class);

        jsonMapper = new ObjectMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
