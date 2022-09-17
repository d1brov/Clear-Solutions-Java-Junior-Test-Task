package com.clearsolutions.testassignment.web.controller;

import com.clearsolutions.testassignment.testingentities.TestingEntities;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
public class UserControllerTest_findByBirthdateRange extends UserControllerTest {

    private final String url = baseUrl+"/birthdate";

    @Test
    void findUsers_validBirthdateRange() throws Exception {
        when(mockUserService.findInBirthdateRange(any(), any()))
                .thenReturn(
                        Collections.singletonList(TestingEntities.getValidUser()));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(url)
                .param("from", "1990-01-01")
                .param("to", "2001-03-08");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").isNotEmpty());
    }

    @Test
    void findUsers_invalidBirthdateRange() throws Exception {
        when(mockUserService.findInBirthdateRange(any(), any()))
                .thenThrow(ConstraintViolationException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(url)
                .param("from", "2000-01-01")
                .param("to", "1990-03-08");

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }
}
