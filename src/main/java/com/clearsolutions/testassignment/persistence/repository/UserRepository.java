package com.clearsolutions.testassignment.persistence.repository;

import com.clearsolutions.testassignment.persistence.model.User;

public interface UserRepository {
    User findByEmail(String email);
    User save(User user);
}
