package com.clearsolutions.testassignment.validation;

import com.clearsolutions.testassignment.web.dto.DateRangeDto;

import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<DateRange, DateRangeDto> {
    @Override
    public boolean isValid(DateRangeDto value, ConstraintValidatorContext context) {
        LocalDate from = value.getFrom();
        LocalDate to = value.getTo();

        if (from == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{message.daterange.from.notnull}")
                    .addConstraintViolation();
            return false;
        }

        if (to == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{message.daterange.to.notnull}")
                    .addConstraintViolation();
            return false;
        }

        return value.getTo().isAfter(value.getFrom());
    }
}