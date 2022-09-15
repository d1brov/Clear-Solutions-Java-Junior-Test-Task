package com.clearsolutions.testassignment.service;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.web.exception.UserWithIdNotFoundException;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static com.clearsolutions.testassignment.testingentities.TestingEntities.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsersServiceTest_delete extends UsersServiceTest {

    @Test
    void with_existingUserId() {
        Integer existingUserId = 3;
        User user = getValidUser();
        user.setId(existingUserId);
        when(mockUserRepository.findById(existingUserId)).thenReturn(Optional.of(user));

        User deletedUser = usersService.delete(existingUserId);

        verify(mockUserRepository).delete(any(User.class));
        assertThat(deletedUser).isNotNull();
    }

    @Test
    void with_nonExistingUserId() {
        Integer nonExistingUserId = 3;
        when(mockUserRepository.findById(nonExistingUserId)).thenReturn(Optional.ofNullable(null));

        assertThrows(UserWithIdNotFoundException.class, () ->
                usersService.delete(nonExistingUserId));
        verify(mockUserRepository, never()).delete(any(User.class));
    }
}