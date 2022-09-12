package com.clearsolutions.testassignment.web.validation;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class MinimumAgeValidator implements ConstraintValidator<MinimumAge, LocalDate> {

    @Value("${validation.user.age.minimum.years}")
    private Integer minimumUserAgeYears;

    @Override
    public boolean isValid(final LocalDate birthDate, final ConstraintValidatorContext context) {
        int userAgeYears = Period.between(birthDate, LocalDate.now()).getYears();
        return userAgeYears >= minimumUserAgeYears;
    }
}