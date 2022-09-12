package com.clearsolutions.testassignment.persistence.repository;

import com.clearsolutions.testassignment.persistence.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User findByEmail(String email);
    Optional<User> findById(Integer id);
    List<User> findInBirthdateRange(LocalDate fromDate, LocalDate tillDate);
    User save(User user);
    void delete(User user);
}
