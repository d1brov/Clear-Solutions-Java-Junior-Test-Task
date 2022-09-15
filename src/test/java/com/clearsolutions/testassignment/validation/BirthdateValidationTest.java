package com.clearsolutions.testassignment.validation;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.validation.order.ValidationGroupSequence;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static com.clearsolutions.testassignment.testingentities.TestingEntities.getValidUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BirthdateValidationTest {
    private final int maturityAge = 18;

    @Autowired // should be autowired because of external constants in validator
    private Validator validator;

    @Test
    public void with_matureAge() {
        LocalDate birthdate = LocalDate.now().minusYears(maturityAge).minusDays(1);
        User user = getValidUser();
        user.setBirthDate(birthdate);

        Set<ConstraintViolation<User>> violations = validator.validate(user, ValidationGroupSequence.class);

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void with_immatureAge() {
        LocalDate birthdate = LocalDate.now().minusYears(maturityAge).plusDays(1);
        User user = getValidUser();
        user.setBirthDate(birthdate);

        Set<ConstraintViolation<User>> violations = validator.validate(user, ValidationGroupSequence.class);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void with_birthdateInFuture() {
        LocalDate birthdate = LocalDate.now().plusDays(1);
        User user = getValidUser();
        user.setBirthDate(birthdate);

        Set<ConstraintViolation<User>> violations = validator.validate(user, ValidationGroupSequence.class);

        assertThat(violations.size()).isEqualTo(1);
    }
}