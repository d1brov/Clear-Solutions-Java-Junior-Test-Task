package com.clearsolutions.testassignment.web.exception;

import com.clearsolutions.testassignment.web.dto.ErrorDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ErrorDto(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<Object> onConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = null;
        if (ex.getConstraintViolations() != null) {
            errors = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());
        }
        return new ResponseEntity<>(new ErrorDto(errors), BAD_REQUEST);
    }

    @ExceptionHandler(UserWithIdNotFoundException.class)
    private ResponseEntity<Object> notFound(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorDto(ex.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(InvalidParameterNameException.class)
    private ResponseEntity<Object> badRequest(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorDto(ex.getMessage()), BAD_REQUEST);
    }
}