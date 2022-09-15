package com.clearsolutions.testassignment.service;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.web.dto.UserDataDto;
import com.clearsolutions.testassignment.web.exception.UserWithIdNotFoundException;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static com.clearsolutions.testassignment.testingentities.TestingEntities.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsersServiceTest_update extends UsersServiceTest {

    @Test
    void updateUser_byNonExisting_UserId() {
        Integer nonExistingUserId = 3;
        when(mockUserRepository.findById(nonExistingUserId)).thenReturn(Optional.ofNullable(null));

        assertThrows(UserWithIdNotFoundException.class, () ->
                usersService.update(nonExistingUserId, getValidUserDataDto()));
        verify(mockUserRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_withValid_UserDataDto() {
        Integer existingId = 3;
        UserDataDto validUserDataDto = getValidUserDataDto();
        User user = getValidUser();
        user.setId(existingId);
        when(mockUserRepository.findById(existingId)).thenReturn(Optional.of(user));

        usersService.update(existingId, validUserDataDto);

        verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void updateUser_withInvalid_UserDataDto() {
        Integer existingId = 3;
        UserDataDto invalidUserDataDto = getInvalidUserDataDto();
        when(mockUserRepository.findById(existingId)).thenReturn(Optional.of(new User()));

        assertThrows(ConstraintViolationException.class, () ->
                usersService.update(existingId, invalidUserDataDto));
        verify(mockUserRepository, never()).save(any(User.class));
    }
}