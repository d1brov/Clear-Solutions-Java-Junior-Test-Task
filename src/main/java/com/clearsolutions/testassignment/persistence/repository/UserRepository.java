package com.clearsolutions.testassignment.persistence.repository;

import com.clearsolutions.testassignment.persistence.model.User;

import java.util.Optional;

public interface UserRepository {
    User findByEmail(String email);
    Optional<User> findById(Integer id);
    User save(User user);
    void delete(User user);
}
