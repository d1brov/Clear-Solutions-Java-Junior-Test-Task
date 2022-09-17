package com.clearsolutions.testassignment.persistence.repository.jpa;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.persistence.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface UserJpaRepository extends UserRepository, JpaRepository<User, Integer> {
    @Override
    @Query(value = "SELECT * FROM users WHERE birth_date >= ?1 AND birth_date <= ?2", nativeQuery = true)
    List<User> findInBirthdateRange(LocalDate fromDate, LocalDate tillDate);
}