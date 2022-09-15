package com.clearsolutions.testassignment.web.controller;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.web.dto.UserDto;
import com.clearsolutions.testassignment.web.exception.UserWithIdNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.clearsolutions.testassignment.testingentities.TestingEntities.getValidUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.springframework.http.HttpStatus.*;

public class UserControllerTest_deleteById extends UserControllerTest{

    private final String baseUrl = "/users/3";

    @Test
    void deleteUser_byExisting_userId() throws Exception {
        User deletedUser = getValidUser();

        lenient().when(mockService.delete(any())).thenReturn(deletedUser);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(baseUrl);

        MockHttpServletResponse response = mvc.perform(request)
                .andReturn()
                .getResponse();

        String contentAsString = response.getContentAsString();
        UserDto returnedUserDto = jsonMapper.readValue(contentAsString, UserDto.class);

        assertThat(response.getStatus()).isEqualTo(OK.value());
        assertThat(returnedUserDto).usingRecursiveComparison().isEqualTo(deletedUser);
    }

    @Test
    void deleteUser_byNonExisting_userId() throws Exception {
        lenient().when(mockService.delete(any())).thenThrow(UserWithIdNotFoundException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(baseUrl);

        MockHttpServletResponse response = mvc.perform(request)
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(NOT_FOUND.value());
    }
}