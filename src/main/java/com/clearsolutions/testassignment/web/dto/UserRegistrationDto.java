package com.clearsolutions.testassignment.web.dto;

import com.clearsolutions.testassignment.web.validation.MinimumAge;
import com.clearsolutions.testassignment.web.validation.UniqueEmail;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class UserRegistrationDto {
    @NotBlank(message = "Email can't be empty")
    @Email(message = "Email must be valid")
    @UniqueEmail(message = "User with this email already registered")
    private String email;

    @NotBlank(message = "First name can't be empty")
    private String firstName;

    @NotBlank(message = "Last name can't be empty")
    private String lastName;

    @NotNull(message = "Birth date can't be empty")
    @Past(message = "Birth date must be in past")
    @MinimumAge(message = "Invalid minimum age") // todo how to pass external constant to message correctly?
    private LocalDate birthDate;

    private String phone;

    private String address;
}