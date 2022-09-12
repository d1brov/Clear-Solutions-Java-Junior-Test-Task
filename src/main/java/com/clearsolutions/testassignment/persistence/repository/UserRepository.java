package com.clearsolutions.testassignment.persistence.repository;

import com.clearsolutions.testassignment.persistence.model.User;

public interface UserRepository {
    User save(User user);
}
