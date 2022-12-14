package com.clearsolutions.testassignment.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = BirthdateValidator.class)
public @interface Birthdate {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}