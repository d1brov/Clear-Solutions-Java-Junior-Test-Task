package com.clearsolutions.testassignment.web.validation;

import com.clearsolutions.testassignment.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(final String email, final ConstraintValidatorContext context) {
        return userRepository.findByEmail(email) == null;
    }
}