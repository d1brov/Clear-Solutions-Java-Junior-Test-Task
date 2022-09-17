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
    private UserMapper mapper;
//    public UserMapperTest() {
//        this.mapper = Mappers.getMapper(UserMapper.class); // why not instantiating
//    }

    @Test
    void updateEmptyUser_withDto() {
        User user = new User();

        UserDataDto userDto = new UserDataDto(
                "its@mail.com",
                "name",
                "lastName",
                LocalDate.now(),
                null,
                null);

        mapper.updateUserFromDto(userDto, user);

        assertThat(user)
                .usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(userDto);
    }

    @Test
    void updateUser_withDto() {
        User originalUser = getValidUser();
        User updUser = getValidUser();

        String updMail = "new@email.com";
        String updPhone = "new_phone";
        UserDataDto userDto = new UserDataDto(
                updMail,
                null,
                null,
                null,
                updPhone,
                null);

        mapper.updateUserFromDto(userDto, updUser);

        assertThat(updUser).usingRecursiveComparison().ignoringFields("email", "phone").isEqualTo(originalUser);
        assertThat(updUser.getEmail()).isEqualTo(updMail);
        assertThat(updUser.getPhone()).isEqualTo(updPhone);
    }

    @Test
    void updateUser_withEmptyDto() {
        User originalUser = getValidUser();
        User updUser = getValidUser();

        UserDataDto userDto = new UserDataDto();

        mapper.updateUserFromDto(userDto, updUser);

        assertThat(updUser).usingRecursiveComparison().isEqualTo(originalUser);
    }
}