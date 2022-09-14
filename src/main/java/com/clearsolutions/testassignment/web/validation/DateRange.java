package com.clearsolutions.testassignment.web.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
public @interface DateRange {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}