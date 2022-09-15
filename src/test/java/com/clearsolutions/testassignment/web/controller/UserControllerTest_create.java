package com.clearsolutions.testassignment.web.controller;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.web.dto.UserDataDto;
import com.clearsolutions.testassignment.web.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import javax.validation.ConstraintViolationException;

import static com.clearsolutions.testassignment.testingentities.TestingEntities.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

public class UserControllerTest_create extends UserControllerTest{

    private final String baseUrl = "/users";

    @Test
    void createUser_withValid_userDataDto() throws Exception {
        User createdUser = getValidUser();
        createdUser.setId(3);
        when(mockService.create(any(UserDataDto.class))).thenReturn(
                createdUser);
        UserDataDto userDataToCreate = userMapper.convertToUserDataDto(createdUser);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(baseUrl)
                .contentType(APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(userDataToCreate));

        MockHttpServletResponse response = mvc.perform(request)
                .andReturn()
                .getResponse();

        String contentAsString = response.getContentAsString();
        UserDto returnedUserDto = jsonMapper.readValue(contentAsString, UserDto.class);

        assertThat(response.getStatus()).isEqualTo(CREATED.value());
        assertThat(returnedUserDto).usingRecursiveComparison().isEqualTo(createdUser);
    }

    @Test
    void createUser_withInvalid_userDataDto() throws Exception {
        lenient().when(mockService.create(any()))
                .thenThrow(ConstraintViolationException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(baseUrl)
                .contentType(APPLICATION_JSON)
                .content("{some invalid UserDataDto JSON}");

        MockHttpServletResponse response = mvc.perform(request)
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        // todo non empty error description check ?
    }
}