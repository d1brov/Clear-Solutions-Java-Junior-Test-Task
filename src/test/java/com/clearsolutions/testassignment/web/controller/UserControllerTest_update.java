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
import static com.clearsolutions.testassignment.testingentities.TestingEntities.getValidUserDataDto;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class UserControllerTest_update extends UserControllerTest{

    private final String baseUrl = "/users/3/update";

    @Test
    void updateUser_byExistingId_withValid_userDataDto() throws Exception {
        String newEmail = "new_valid@email.com";
        UserDataDto updateDto = new UserDataDto();
        updateDto.setEmail(newEmail);

        User updatedUser = getValidUser();
        updatedUser.setEmail(newEmail);

        when(mockService.update(any(Integer.class), any(UserDataDto.class)))
                .thenReturn(updatedUser);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(baseUrl)
                .contentType(APPLICATION_JSON).content(jsonMapper.writeValueAsString(updateDto));

        MockHttpServletResponse response = mvc.perform(request)
                .andReturn()
                .getResponse();

        String contentAsString = response.getContentAsString();
        UserDto returnedUserDto = jsonMapper.readValue(contentAsString, UserDto.class);

        assertThat(response.getStatus()).isEqualTo(OK.value());
        assertThat(returnedUserDto).usingRecursiveComparison().isEqualTo(updatedUser);
    }

    @Test
    void updateUser_byNonExistingId_withValid_userDataDto() throws Exception {
        lenient().when(mockService.update(any(), any()))
                .thenThrow(UserWithIdNotFoundException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(baseUrl)
                .contentType(APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(getValidUserDataDto()));

        MockHttpServletResponse response = mvc.perform(request)
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(NOT_FOUND.value());
        // todo non empty error description check ?
    }

    @Test
    void updateUser_byExistingId_withInvalid_userDataDto() throws Exception {
        lenient().when(mockService.update(any(), any()))
                .thenThrow(ConstraintViolationException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(baseUrl)
                .contentType(APPLICATION_JSON)
                .content("{some invalid UserDataDto JSON}");

        MockHttpServletResponse response = mvc.perform(request)
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        // todo non empty error description check ?
    }
}