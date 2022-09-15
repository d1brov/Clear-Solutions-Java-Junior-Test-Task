package com.clearsolutions.testassignment.web.dto;

import com.clearsolutions.testassignment.web.exception.InvalidParameterNameException;
import lombok.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDto {
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phone;
    private String address;

    public UserDataDto(Map<String, String> parameters) {
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            try {
                Field field = UserDataDto.class.getDeclaredField(parameter.getKey());
                field.setAccessible(true);
                field.set(this, parameter.getValue());
            } catch (NoSuchFieldException e) {
                throw new InvalidParameterNameException(parameter.getKey());
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cant access field " + parameter.getKey(), e.getCause());
            }
        }
    }
}