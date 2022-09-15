package com.clearsolutions.testassignment.testingentities;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.web.dto.UserDataDto;

import java.time.LocalDate;

public class TestingEntities {
    public static UserDataDto getValidUserDataDto() {
        LocalDate matureBirthday = LocalDate.now().minusYears(30);
        return new UserDataDto(
                "dto@mail.com",
                "Dtoname",
                "Dtolastname",
                matureBirthday,
                "+380000000111",
                "NYC, 9999 Broadway"
        );
    }

    public static UserDataDto getInvalidUserDataDto() {
        LocalDate immatureBirthdate = LocalDate.now().minusYears(1);
        return new UserDataDto(
                "invalid@mail[[com",
                "",
                "Dtolastname",
                immatureBirthdate,
                "+380000000111",
                "NYC, 9999 Broadway"
        );
    }

    public static User getValidUser() {
        LocalDate matureBirthday = LocalDate.now().minusYears(30);
        return new User(
                3,
                "valid@mail.com",
                "ValidName",
                "ValidLastName",
                matureBirthday,
                "+380000000000",
                "NYC, 4121 Broadway"
        );
    }
}
