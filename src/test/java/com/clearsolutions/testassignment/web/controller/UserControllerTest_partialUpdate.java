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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
public class UserControllerTest_partialUpdate extends UserControllerTest {

    @Test
    void patchUser_byExistingId_withValidParameters_success() throws Exception {
        User originalUser = TestingEntities.getValidUser();
        Integer id = originalUser.getId();

        String newEmail = "new@email.com";
        String newName = "Newname";
        User updatingUser = TestingEntities.getValidUser();
        updatingUser.setEmail(newEmail);
        updatingUser.setFirstName(newName);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("email", newEmail);
        parameters.put("firstName", newName);

        when(mockedUserRepository.findById(id))
                .thenReturn(Optional.of(originalUser));

        when(mockedUserRepository.save(any(User.class)))
                .thenReturn(updatingUser);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .patch(baseUrl + "/" + originalUser.getId())
                .contentType(APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(parameters));

        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        UserDto updatedUser = jsonMapper.readValue(response.getContentAsString(), UserDto.class);

        assertThat(updatingUser).usingRecursiveComparison().isEqualTo(updatedUser);
        verify(mockedUserRepository).save(any(User.class));
    }

    @Test
    void patchUser_byExistingId_withInvalidParameterValues_badRequest() throws Exception {
        String invalidMail = "new___email.com";
        String invalidName = "";

        Map<String, String> parameters = new HashMap<>();
        parameters.put("email", invalidMail);
        parameters.put("firstName", invalidName);

        when(mockedUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(TestingEntities.getValidUser()));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .patch(baseUrl + "/3")
                .contentType(APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(parameters));

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
        verify(mockedUserRepository, never()).save(any(User.class));
    }

    @Test
    void patchUser_byExistingId_withInvalidParameterNames_badRequest() throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("emailllll", "valid@mail.com");
        parameters.put("firstNamee", "Validname");

        when(mockedUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(TestingEntities.getValidUser()));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .patch(baseUrl + "/3")
                .contentType(APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(parameters));

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
        verify(mockedUserRepository, never()).save(any(User.class));
    }

    @Test
    void patchUser_byNonExistingId_withValidParameters_notFound() throws Exception {
        when(mockedUserRepository.findById(anyInt()))
                .thenThrow(UserWithIdNotFoundException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .patch(baseUrl + "/3")
                .contentType(APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(
                        Collections.singletonMap("email", "valid@email.com")));

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
        verify(mockedUserRepository, never()).save(any(User.class));
    }
}
