package com.clearsolutions.testassignment.validation;

import com.clearsolutions.testassignment.web.dto.DateRangeDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DateRangeValidationTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @Test
    public void with_validRange() {
        LocalDate from = LocalDate.now();
        LocalDate to = from.plusYears(3);
        DateRangeDto range = new DateRangeDto(from, to);

        Set<ConstraintViolation<DateRangeDto>> violations = validator.validate(range);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void with_fromAfterTo() {
        LocalDate from = LocalDate.now();
        LocalDate to = from.minusYears(3);
        DateRangeDto range = new DateRangeDto(from, to);

        Set<ConstraintViolation<DateRangeDto>> violations = validator.validate(range);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void with_fromNull() {
        LocalDate from = null;
        LocalDate to = LocalDate.now();
        DateRangeDto range = new DateRangeDto(from, to);

        Set<ConstraintViolation<DateRangeDto>> violations = validator.validate(range);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void with_toNull() {
        LocalDate from = LocalDate.now();
        DateRangeDto range = new DateRangeDto(from, null);

        Set<ConstraintViolation<DateRangeDto>> violations = validator.validate(range);

        assertThat(violations.size()).isEqualTo(1);
    }
}