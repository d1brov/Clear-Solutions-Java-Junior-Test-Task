package com.clearsolutions.testassignment.web.util;

import com.clearsolutions.testassignment.web.dto.UserDataDto;
import com.clearsolutions.testassignment.web.exception.InvalidParameterNameException;

import java.lang.reflect.Field;
import java.util.Map;

public class ParametersToDtoConverter {
    public static UserDataDto convert(Map<String, String> parameters) {
        UserDataDto userDataDto = new UserDataDto();
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            try {
                Field field = UserDataDto.class.getDeclaredField(parameter.getKey());
                field.setAccessible(true);
                field.set(userDataDto, parameter.getValue());
            } catch (NoSuchFieldException e) {
                throw new InvalidParameterNameException(parameter.getKey());
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cant access field", e.getCause());
            }
        }
        return userDataDto;
    }
}
