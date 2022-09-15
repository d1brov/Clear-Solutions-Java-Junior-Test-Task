package com.clearsolutions.testassignment.service;

import com.clearsolutions.testassignment.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validator;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {
    @Mock
    protected UserRepository mockUserRepository;

    @Autowired
    private Validator validator;

    protected UsersService usersService;

    @BeforeEach
    void setup() {
        usersService = new UsersService(mockUserRepository, validator);
    }
}
