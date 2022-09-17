package com.clearsolutions.testassignment.web.controller;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.testingentities.TestingEntities;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import javax.validation.ConstraintViolationException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
public class UserControllerTest_create extends UserControllerTest {

    @Test
    void createUser_validDto_success() throws Exception {
        User validUser = TestingEntities.getValidUser();

        when(mockUserService.create(any()))
                .thenReturn(validUser);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(baseUrl)
                .contentType(APPLICATION_JSON)
                .content("{}"); // valid json here

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.email").value(validUser.getEmail()));
    }

    @Test
    void createUser_invalidDto_fail() throws Exception {
        when(mockUserService.create(any()))
                .thenThrow(ConstraintViolationException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(baseUrl)
                .contentType(APPLICATION_JSON)
                .content("{invalid json here}");

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }
}
