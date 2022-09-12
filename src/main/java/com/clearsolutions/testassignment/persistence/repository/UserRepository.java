package com.clearsolutions.testassignment.persistence.repository;

import com.clearsolutions.testassignment.persistence.model.User;

import java.util.List;

public interface UserRepository {
    User findByEmail(String email);
    User findByPhone(String phone);
    User save(User user);
    List<User> findAll();
}
