package com.clearsolutions.testassignment.service;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.web.dto.DateRangeDto;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.clearsolutions.testassignment.testingentities.TestingEntities.getValidUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsersServiceTest_findInBirthdateRange extends UsersServiceTest {

    @Test
    void with_invalidDateRange() {
        LocalDate from = LocalDate.now().minusYears(1);
        LocalDate to = from.minusDays(1);
        DateRangeDto invalidDateRange = new DateRangeDto(from, to);

        assertThrows(ConstraintViolationException.class, () ->
                usersService.findInBirthdateRange(invalidDateRange));
        verify(mockUserRepository, never())
                .findInBirthdateRange(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    void with_validDateRange_nothingFound() {
        LocalDate from = LocalDate.now().minusYears(1);
        LocalDate to = from.plusYears(1);
        DateRangeDto validDateRange = new DateRangeDto(from, to);
        when(mockUserRepository.findInBirthdateRange(from, to)).thenReturn(new ArrayList<>());

        List<User> foundUsers = usersService.findInBirthdateRange(validDateRange);

        verify(mockUserRepository).findInBirthdateRange(from, to);
        assertThat(foundUsers).isNotNull();
        assertThat(foundUsers.size()).isEqualTo(0);
    }

    @Test
    void with_validDateRange() {
        LocalDate from = LocalDate.now().minusYears(1);
        LocalDate to = from.plusYears(1);
        DateRangeDto validDateRange = new DateRangeDto(from, to);
        List<User> usersToFind = new ArrayList<>();
        usersToFind.add(getValidUser());
        when(mockUserRepository.findInBirthdateRange(from, to)).thenReturn(usersToFind);

        List<User> foundUsers = usersService.findInBirthdateRange(validDateRange);

        verify(mockUserRepository).findInBirthdateRange(from, to);
        assertThat(foundUsers).isNotNull();
        assertThat(foundUsers.size()).isEqualTo(1);
    }
}