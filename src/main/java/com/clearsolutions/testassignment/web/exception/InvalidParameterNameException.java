package com.clearsolutions.testassignment.web.exception;

public class InvalidParameterNameException extends RuntimeException {
    public InvalidParameterNameException(String invalidParameterName) {
        super(String.format("Parameter name: '%s' is invalid", invalidParameterName));
    }
}

