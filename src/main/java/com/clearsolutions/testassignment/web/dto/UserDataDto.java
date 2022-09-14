package com.clearsolutions.testassignment.web.dto;

import lombok.*;
import java.time.LocalDate;

@Data
public class UserDataDto {
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phone;
    private String address;
}