package com.clearsolutions.testassignment.web.dto;

import com.clearsolutions.testassignment.web.exception.InvalidParameterNameException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;

class UserDataDtoTest {
    @Test
    void initUserDataDto_withEmptyParametersMap_success() {
        UserDataDto userDataDto = new UserDataDto(new HashMap<>());

        assertThat(userDataDto).hasAllNullFieldsOrProperties();
    }

    @Test
    void initUserDataDto_withInvalidParameterNamesMap_fail() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("email", "valid@mail.com");
        parameters.put("invalid", "parameter");

        assertThrows(InvalidParameterNameException.class, () ->
                new UserDataDto(parameters));
    }

    @Test
    void initUserDataDto_withValidMap_success() {
        String emailFieldName = "email";
        String firstNameFieldName = "firstName";
        Map<String, String> parameters = new HashMap<>();
        parameters.put(emailFieldName, "valid@mail.com");
        parameters.put(firstNameFieldName, "Vasyl");

        UserDataDto userDataDto = new UserDataDto(parameters);

        assertThat(userDataDto).hasAllNullFieldsOrPropertiesExcept(emailFieldName, firstNameFieldName);
        assertThat(userDataDto.getEmail()).isEqualTo(parameters.get(emailFieldName));
        assertThat(userDataDto.getFirstName()).isEqualTo(parameters.get(firstNameFieldName));
    }

}