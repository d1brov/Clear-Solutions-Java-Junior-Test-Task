package com.clearsolutions.testassignment.web.util;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.web.dto.UserDataDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static com.clearsolutions.testassignment.testingentities.TestingEntities.getValidUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper mapper;

    @Test
    void updateEmptyUser_withDto() {
        // given empty user
        User user = new User();

        // given user dto with data
        UserDataDto userDto = new UserDataDto(
                "its@mail.com",
                "name",
                "lastName",
                LocalDate.now(),
                null,
                null);

        // update user with dto
        mapper.updateUserFromDto(userDto, user);

        // user updated all fields
        assertThat(user)
                .usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(userDto);
    }

    @Test
    void updateUser_withDto() {
        // given user
        User originalUser = getValidUser();
        User updUser = getValidUser();

        // given user dto with data
        String updMail = "new@email.com";
        String updPhone = "new_phone";
        UserDataDto userDto = new UserDataDto(
                updMail,
                null,
                null,
                null,
                updPhone,
                null);

        // update user with dto
        mapper.updateUserFromDto(userDto, updUser);

        // user updated only non-null values
        assertThat(updUser).usingRecursiveComparison().ignoringFields("email", "phone").isEqualTo(originalUser);
        assertThat(updUser.getEmail()).isEqualTo(updMail);
        assertThat(updUser.getPhone()).isEqualTo(updPhone);
    }

    @Test
    void updateUser_withEmptyDto() {
        // given user
        User originalUser = getValidUser();
        User updUser = getValidUser();

        // given empty user data dto
        UserDataDto userDto = new UserDataDto();

        // update user with empty dto
        mapper.updateUserFromDto(userDto, updUser);

        // user did not change
        assertThat(updUser).usingRecursiveComparison().isEqualTo(originalUser);
    }
}