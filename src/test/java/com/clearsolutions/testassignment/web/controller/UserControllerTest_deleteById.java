package com.clearsolutions.testassignment.web.controller;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.testingentities.TestingEntities;
import com.clearsolutions.testassignment.web.exception.UserWithIdNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
public class UserControllerTest_deleteById extends UserControllerTest {

    @Test
    void deleteUser_byExisting_userId() throws Exception {
        User validUser = TestingEntities.getValidUser();
        Integer id = validUser.getId();

        when(mockUserService.delete(any()))
                .thenReturn(validUser);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(baseUrl + "/" + id)
                .contentType(APPLICATION_JSON)
                .content("{}"); // valid json here

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void deleteUser_byNonExisting_userId() throws Exception {
        when(mockUserService.delete(any()))
                .thenThrow(UserWithIdNotFoundException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(baseUrl + "/" + 3)
                .contentType(APPLICATION_JSON)
                .content("{}"); // valid json here

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }
}
