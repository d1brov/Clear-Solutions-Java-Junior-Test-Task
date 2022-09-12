package com.clearsolutions.testassignment.persistence.repository;

import com.clearsolutions.testassignment.persistence.model.User;
import java.util.List;

public interface UserRepository {
    List<User> findAll();
}
