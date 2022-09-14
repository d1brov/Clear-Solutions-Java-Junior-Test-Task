package com.clearsolutions.testassignment.web.dto;

import com.clearsolutions.testassignment.web.validation.MinimumAge;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class UserDataDto {
    @NotBlank(message = "{message.email.notblank}")
    @Email(message = "{message.email.format}")
    private String email;

    @NotBlank(message = "{message.firstname.notblank}")
    private String firstName;

    @NotBlank(message = "{message.lastname.notblank}")
    private String lastName;

    @NotNull(message = "{message.birthdate.notnull}")
    @Past(message = "{message.birthdate.past}")
    @MinimumAge(message = "{message.minimum.age.years}")
    private LocalDate birthDate;

    private String phone;

    private String address;
}