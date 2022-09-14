package com.clearsolutions.testassignment.web.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
public class ErrorDto {
    private final LocalDateTime timestamp;
    private final List<String> errorMessages;

    public ErrorDto(List<String> errorMessages) {
        this.timestamp = LocalDateTime.now();
        this.errorMessages = errorMessages;
    }

    public ErrorDto(String errorMessage) {
        this.timestamp = LocalDateTime.now();
        this.errorMessages = Collections.singletonList(errorMessage);
    }
}