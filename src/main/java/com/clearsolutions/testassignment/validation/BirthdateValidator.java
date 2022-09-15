package com.clearsolutions.testassignment.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:validation.properties")
public class BirthdateValidator implements ConstraintValidator<Birthdate, LocalDate> {

    @Value("${user.age.minimum.years}")
    private int minimumUserAgeYears;

    @Override
    public boolean isValid(final LocalDate birthDate, ConstraintValidatorContext context) {
        boolean isOldEnough = LocalDate.now().minusYears(minimumUserAgeYears).isAfter(birthDate);
        return isOldEnough;
    }
}