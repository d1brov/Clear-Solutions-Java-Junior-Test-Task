package com.clearsolutions.testassignment.web.exception;

public class UserWithIdNotFoundException extends RuntimeException {
    public UserWithIdNotFoundException(Integer id) {
        super(String.format("User with id: '%d' can not be found", id));
    }
}

