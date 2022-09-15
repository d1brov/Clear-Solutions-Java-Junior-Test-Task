package com.clearsolutions.testassignment.web.controller;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.web.dto.UserDataDto;
import com.clearsolutions.testassignment.web.dto.UserDto;
import com.clearsolutions.testassignment.web.exception.UserWithIdNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.ConstraintViolationException;

import static com.clearsolutions.testassignment.testingentities.TestingEntities.getValidUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

public class UserControllerTest_updateByParameters extends UserControllerTest{

    private final String baseUrl = "/users/3/update/parameters";

    @Test
    void with_existingId_validParameters() throws Exception {
        String newEmail = "new_valid@email.com";
        UserDataDto updateDto = new UserDataDto();
        updateDto.setEmail(newEmail);

        User updatedUser = getValidUser();
        updatedUser.setEmail(newEmail);

        when(mockService.update(any(Integer.class), any(UserDataDto.class)))
                .thenReturn(updatedUser);

        String validParameterName = "email";
        String validParameterValue = "new@mail.com";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(baseUrl)
                .param(validParameterName, validParameterValue);

        MockHttpServletResponse response = mvc.perform(request)
                .andReturn()
                .getResponse();

        String contentAsString = response.getContentAsString();
        UserDto returnedUserDto = jsonMapper.readValue(contentAsString, UserDto.class);

        assertThat(response.getStatus()).isEqualTo(OK.value());
        assertThat(returnedUserDto).usingRecursiveComparison().isEqualTo(updatedUser);
    }

    @Test
    void with_nonExistingUserId_validParameters() throws Exception {
        lenient().when(mockService.update(any(), any()))
                .thenThrow(UserWithIdNotFoundException.class);

        String validParameterName = "email";
        String validParameterValue = "new@mail.com";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(baseUrl)
                .param(validParameterName, validParameterValue);

        MockHttpServletResponse response = mvc.perform(request)
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(NOT_FOUND.value());
        // todo non empty error description check ?
    }

    @Test
    void with_existingId_invalidParameters() throws Exception {
        lenient().when(mockService.update(any(), any()))
                .thenThrow(ConstraintViolationException.class);

        String validParameterName = "email";
        String invalidParameterValue = "newMailInvalid.com";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(baseUrl)
                .param(validParameterName, invalidParameterValue);

        MockHttpServletResponse response = mvc.perform(request)
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        // todo non empty error description check ?
    }

    @Test
    void with_existingId_invalidParameterNames() throws Exception {
        String invalidParameterName = "eeeemail";
        String validParameterValue = "new@mail.com";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(baseUrl)
                .param(invalidParameterName, validParameterValue);

        MockHttpServletResponse response = mvc.perform(request)
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        // todo non empty error description check ?
    }
}