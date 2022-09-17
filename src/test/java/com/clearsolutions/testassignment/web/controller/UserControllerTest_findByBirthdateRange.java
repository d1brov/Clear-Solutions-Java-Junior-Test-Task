package com.clearsolutions.testassignment.web.controller;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.testingentities.TestingEntities;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
public class UserControllerTest_findByBirthdateRange extends UserControllerTest {

    private final String url = baseUrl+"/birthdate";

    @Test
    void getUsers_byValidBirthdateRange_success() throws Exception {
        LocalDate from = LocalDate.now().minusYears(10);
        LocalDate to = from.plusYears(20);
        User foundUser = TestingEntities.getValidUser();
        List<User> foundUsers = Collections.singletonList(foundUser);

        when(mockedUserRepository.findInBirthdateRange(from, to))
                .thenReturn(foundUsers);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(url)
                .param("from", from.toString())
                .param("to", to.toString());

        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        List<User> foundUsersList = jsonMapper.readerForListOf(User.class)
                .readValue(response.getContentAsString());

        assertThat(foundUsersList.size()).isEqualTo(1);
        assertThat(foundUsersList.get(0)).usingRecursiveComparison().isEqualTo(foundUser);
        verify(mockedUserRepository, never()).save(any(User.class));
    }

    @Test
    void getUsers_byInvalidBirthdateRange_badRequest() throws Exception {
        LocalDate from = LocalDate.now().plusYears(20);
        LocalDate to = from.minusYears(20);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(url)
                .param("from", from.toString())
                .param("to", to.toString());

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
        verify(mockedUserRepository, never()).findInBirthdateRange(from, to);
    }
}
