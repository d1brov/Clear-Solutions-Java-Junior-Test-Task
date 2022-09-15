package com.clearsolutions.testassignment.service;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.web.dto.UserDataDto;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolationException;

import static com.clearsolutions.testassignment.testingentities.TestingEntities.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsersServiceTest_create extends UsersServiceTest {

    @Test
    void with_validUserDataDto() {
        UserDataDto validUserDataDto = getValidUserDataDto();
        User user = getValidUser();
        when(mockUserRepository.save(any(User.class))).thenReturn(user);

        User createdUser = usersService.create(validUserDataDto);

        verify(mockUserRepository).save(any(User.class));
        assertThat(createdUser).isNotNull();
    }

    @Test
    void with_invalidUserDto() {
        UserDataDto invalidUserDataDto = getInvalidUserDataDto();

        assertThrows(ConstraintViolationException.class, () ->
                usersService.create(invalidUserDataDto));
        verify(mockUserRepository, never()).save(any(User.class));
    }
}