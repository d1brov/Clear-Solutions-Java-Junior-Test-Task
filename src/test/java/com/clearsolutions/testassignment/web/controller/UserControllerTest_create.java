package com.clearsolutions.testassignment.web.controller;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.testingentities.TestingEntities;
import com.clearsolutions.testassignment.web.dto.UserDataDto;
import com.clearsolutions.testassignment.web.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
public class UserControllerTest_create extends UserControllerTest {

    @Test
    void createUser_withValidDto_success() throws Exception {
        UserDataDto userDataDto = TestingEntities.getValidUserDataDto();
        User userToSave = userMapper.convertToUser(userDataDto);

        when(mockedUserRepository
                .save(any(User.class)))
                .thenReturn(userToSave);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(baseUrl)
                .contentType(APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(userDataDto));

        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        UserDto savedUser = jsonMapper.readValue(response.getContentAsString(), UserDto.class);

        assertThat(userToSave).usingRecursiveComparison().isEqualTo(savedUser);
        verify(mockedUserRepository).save(any(User.class));
    }

    @Test
    void createUser_invalidDto_badRequest() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(baseUrl)
                .contentType(APPLICATION_JSON)
                .content(jsonMapper
                        .writeValueAsString(TestingEntities.getInvalidUserDataDto()));

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
        verify(mockedUserRepository, never()).save(any(User.class));
    }
}
