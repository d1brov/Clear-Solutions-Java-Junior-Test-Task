package com.clearsolutions.testassignment.web.controller;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.testingentities.TestingEntities;
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
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
public class UserControllerTest_deleteById extends UserControllerTest {

    @Test
    void deleteUser_byExistingId_success() throws Exception {
        User userToDelete = TestingEntities.getValidUser();
        Integer id = userToDelete.getId();

        when(mockedUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(userToDelete));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(baseUrl + "/" + id)
                .contentType(APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(userToDelete));

        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        UserDto deletedUser = jsonMapper.readValue(response.getContentAsString(), UserDto.class);

        assertThat(userToDelete).usingRecursiveComparison().isEqualTo(deletedUser);
        verify(mockedUserRepository).delete(any(User.class));
    }

    @Test
    void deleteUser_byNonExistingId_notFound() throws Exception {
        when(mockedUserRepository.findById(anyInt()))
                .thenThrow(UserWithIdNotFoundException.class);

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
