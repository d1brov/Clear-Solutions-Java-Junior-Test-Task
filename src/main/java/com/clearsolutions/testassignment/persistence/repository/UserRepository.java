package com.clearsolutions.testassignment.persistence.repository;

import com.clearsolutions.testassignment.persistence.model.User;

public interface UserRepository {
    User findByEmail(String email);
    User findByPhone(String phone);
    User save(User user);
}
