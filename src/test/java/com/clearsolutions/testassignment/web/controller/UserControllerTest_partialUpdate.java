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
public class UserControllerTest_partialUpdate extends UserControllerTest {

    @Test
    void patchUser_byExistingId_withValidParameters() throws Exception {
        User validUser = TestingEntities.getValidUser();
        Integer id = validUser.getId();

        when(mockUserService.partialUpdate(any(), any()))
                .thenReturn(validUser);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .patch(baseUrl + "/" + id)
                .contentType(APPLICATION_JSON)
                .content("{}");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void patchUser_byExistingId_withInvalidParameters() throws Exception {
        when(mockUserService.partialUpdate(any(), any()))
                .thenThrow(ConstraintViolationException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .patch(baseUrl + "/3")
                .contentType(APPLICATION_JSON)
                .content("{}");

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void patchUser_byNonExistingId() throws Exception {
        when(mockUserService.partialUpdate(any(), any()))
                .thenThrow(UserWithIdNotFoundException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .patch(baseUrl + "/3")
                .contentType(APPLICATION_JSON)
                .content("{}");

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }
}
