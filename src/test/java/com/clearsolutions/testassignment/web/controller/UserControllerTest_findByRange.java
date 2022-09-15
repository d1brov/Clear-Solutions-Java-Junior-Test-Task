package com.clearsolutions.testassignment.web.controller;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.web.dto.UserDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;

import static com.clearsolutions.testassignment.testingentities.TestingEntities.getValidUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

public class UserControllerTest_findByRange extends UserControllerTest{

    private final String baseUrl = "/users/birthdate";

    /*
    todo later
    Seems that MockMvc can't properly pass parameters from url to Dto

    REQUEST GET /users/birthdate?from=1895-01-01&to=2000-01-01

    @GetMapping("/birthdate")
    ResponseEntity<List<UserDto>> findByRange(DateRangeDto dateRange) {
     */
    @Disabled
    @Test
    void with_validRange_foundUsers() throws Exception {
        List<User> foundUsers = new ArrayList<>();
        foundUsers.add(getValidUser());

        when(mockService.findInBirthdateRange(any()))
                .thenReturn(foundUsers);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(baseUrl)
                .param("from", "1990-01-01")
                .param("to", "2001-03-08");

        MockHttpServletResponse response = mvc.perform(request)
                .andReturn()
                .getResponse();

        String contentAsString = response.getContentAsString();
        List<UserDto> returnedUserDto = jsonMapper
                .readerForListOf(UserDto.class)
                .readValue(contentAsString);

        assertThat(response.getStatus()).isEqualTo(OK.value());
        assertThat(returnedUserDto.size()).isEqualTo(1);
    }
}