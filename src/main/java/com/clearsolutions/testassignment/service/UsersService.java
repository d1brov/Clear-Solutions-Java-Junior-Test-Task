package com.clearsolutions.testassignment.service;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.web.dto.UserDataDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface UsersService {
    User create(UserDataDto userDataDto);

    List<User> findInBirthdateRange(LocalDate from, LocalDate to);

    User update(Integer id, UserDataDto userDataDto);

    User partialUpdate(Integer id, Map<String, String> parameters);

    User delete(Integer id);
}
