package com.clearsolutions.testassignment.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {

    private String beforeFieldName;
    private String afterFieldName;

    @Override
    public void initialize(final DateRange constraintAnnotation) {
        beforeFieldName = constraintAnnotation.before();
        afterFieldName = constraintAnnotation.after();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            final Field beforeDateField = value.getClass().getDeclaredField(beforeFieldName);
            beforeDateField.setAccessible(true);

            final Field afterDateField = value.getClass().getDeclaredField(afterFieldName);
            afterDateField.setAccessible(true);

            final LocalDate beforeDate = (LocalDate) beforeDateField.get(value);
            final LocalDate afterDate = (LocalDate) afterDateField.get(value);

            return beforeDate.equals(afterDate) || beforeDate.isBefore(afterDate);
        } catch (final Exception e) {
            throw new InvalidParameterException(e.getMessage()); // todo check
        }
    }
}