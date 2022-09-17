package com.clearsolutions.testassignment.web.controller;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.testingentities.TestingEntities;
import com.clearsolutions.testassignment.web.dto.UserDataDto;
import com.clearsolutions.testassignment.web.dto.UserDto;
import com.clearsolutions.testassignment.web.exception.UserWithIdNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
public class UserControllerTest_update extends UserControllerTest {

    @Test
    void updateUser_byExistingId_withValidDto_success() throws Exception {
        User originalUser = TestingEntities.getValidUser();
        Integer id = originalUser.getId();
        when(mockedUserRepository.findById(id)).thenReturn(Optional.of(originalUser));

        String newEmail = "new@email.com";
        UserDataDto updatingDto = userMapper.convertToUserDataDto(originalUser);
        updatingDto.setEmail(newEmail);

        User updatedUser = TestingEntities.getInvalidUser();
        updatedUser.setEmail(newEmail);
        when(mockedUserRepository.save(any(User.class))).thenReturn(updatedUser);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(baseUrl + "/" + id)
                .contentType(APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(updatingDto));

        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().isOk()).andReturn().getResponse();

        UserDto updatedUserDto = jsonMapper.readValue(response.getContentAsString(), UserDto.class);

        assertThat(originalUser)
                .usingRecursiveComparison()
                .ignoringFields("email")
                .isEqualTo(originalUser);
        assertThat(updatedUserDto.getEmail()).isEqualTo(newEmail);
        verify(mockedUserRepository).save(any(User.class));
    }

    @Test
    void updateUser_byNonExistingId_withValidDto_notFound() throws Exception {

        when(mockedUserRepository.findById(anyInt()))
                .thenThrow(UserWithIdNotFoundException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(baseUrl + "/3")
                .contentType(APPLICATION_JSON)
                .content(jsonMapper
                        .writeValueAsString(TestingEntities.getValidUserDataDto()));

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
        verify(mockedUserRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_byExistingId_withInvalidDto_badRequest() throws Exception {
        when(mockedUserRepository.findById(any()))
                .thenReturn(Optional.of(TestingEntities.getValidUser()));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(baseUrl + "/" + 3)
                .contentType(APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(
                        TestingEntities.getInvalidUserDataDto())); // valid json here

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
        verify(mockedUserRepository, never()).save(any(User.class));
    }
}
