package com.clearsolutions.testassignment.web.controller;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.testingentities.TestingEntities;
import com.clearsolutions.testassignment.web.exception.UserWithIdNotFoundException;
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
public class UserControllerTest_update extends UserControllerTest {

    @Test
    void updateUser_byExistingId_withValidDto() throws Exception {
        User validUser = TestingEntities.getValidUser();
        Integer id = validUser.getId();

        when(mockUserService.update(any(), any()))
                .thenReturn(validUser);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(baseUrl + "/" + id)
                .contentType(APPLICATION_JSON)
                .content("{}"); // valid json here

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void updateUser_byNonExistingId_withValidDto() throws Exception {
        when(mockUserService.update(any(), any()))
                .thenThrow(UserWithIdNotFoundException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(baseUrl + "/" + 3)
                .contentType(APPLICATION_JSON)
                .content("{}"); // valid json here

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_byExistingId_withInvalidDto() throws Exception {
        when(mockUserService.update(any(), any()))
                .thenThrow(ConstraintViolationException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(baseUrl + "/" + 3)
                .contentType(APPLICATION_JSON)
                .content("{}"); // valid json here

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }
}
