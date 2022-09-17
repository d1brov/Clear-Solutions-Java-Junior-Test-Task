package com.clearsolutions.testassignment.web.controller;

import com.clearsolutions.testassignment.service.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UsersController.class)
public class UserControllerTest {

    @MockBean
    protected UsersServiceImpl mockUserService;

    @Autowired
    protected MockMvc mockMvc;

    protected final String baseUrl = "/users";
}
