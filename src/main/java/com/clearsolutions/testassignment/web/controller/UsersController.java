package com.clearsolutions.testassignment.web.controller;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.persistence.repository.UserRepository;
import com.clearsolutions.testassignment.validation.order.ValidationGroupSequence;
import com.clearsolutions.testassignment.web.dto.DateRangeDto;
import com.clearsolutions.testassignment.web.dto.UserDto;
import com.clearsolutions.testassignment.web.dto.UserDataDto;
import com.clearsolutions.testassignment.web.exception.InvalidParameterNameException;
import com.clearsolutions.testassignment.web.exception.UserWithIdNotFoundException;
import com.clearsolutions.testassignment.web.util.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("users")
public class UsersController {
    private final UserRepository userRepository;
    private final Validator validator;
    private final UserMapper userMapper;

    @Autowired
    public UsersController(UserRepository userRepository, Validator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.userMapper = Mappers.getMapper(UserMapper.class);
    }

    @PostMapping
    ResponseEntity<UserDto> create(@RequestBody UserDataDto userDataDto) {
        User userToSave = userMapper.convertToUser(userDataDto);
        validate(userToSave);
        User createdUser = userRepository.save(userToSave);

        UserDto createdUserDto = userMapper.convertToDto(createdUser);
        return new ResponseEntity<>(createdUserDto, CREATED);
    }

    @GetMapping("/birthdate")
    ResponseEntity<List<UserDto>> findByBirthdateRange(@RequestParam LocalDate from,
                                                       @RequestParam LocalDate to) {
        validate(new DateRangeDto(from, to));
        List<User> foundUsers = userRepository.findInBirthdateRange(from, to);

        List<UserDto> usersInRange = foundUsers
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(usersInRange, OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<UserDto> update(@PathVariable Integer id,
                                   @RequestBody UserDataDto updatingUserDataDto) {
        userRepository.findById(id).orElseThrow(() ->
                new UserWithIdNotFoundException(id));

        User updatingUser = userMapper.convertToUser(updatingUserDataDto);
        updatingUser.setId(id);

        validate(updatingUser);

        User updatedUser = userRepository.save(updatingUser);
        UserDto updatedUserDto = userMapper.convertToDto(updatedUser);
        return new ResponseEntity<>(updatedUserDto, OK);
    }

    @PatchMapping("/{id}")
    ResponseEntity<UserDto> partialUpdate(@PathVariable Integer id,
                                          @RequestBody Map<String, String> parameters) {
        User updatingUser = userRepository.findById(id).orElseThrow(() ->
                new UserWithIdNotFoundException(id));

        setParametersToUser(updatingUser, parameters);
        validate(updatingUser);

        User patchedUser = userRepository.save(updatingUser);

        UserDto updatedUserDto = userMapper.convertToDto(patchedUser);
        return new ResponseEntity<>(updatedUserDto, OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<UserDto> deleteById(@PathVariable Integer id) {
        User deletedUser = userRepository.findById(id).orElseThrow(
                () -> new UserWithIdNotFoundException(id));
        userRepository.delete(deletedUser);

        UserDto deletedUserDto = userMapper.convertToDto(deletedUser);
        return new ResponseEntity<>(deletedUserDto, OK);
    }

    private <T> void validate(T object) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object, ValidationGroupSequence.class);
        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    private void setParametersToUser(User user, Map<String, String> parameters) {
        parameters.forEach((parameterName, parameterValue) -> {
            try {
                Field field = User.class.getDeclaredField(parameterName);
                field.setAccessible(true);
                field.set(user, parameterValue);
            } catch (NoSuchFieldException | IllegalArgumentException e) {
                throw new InvalidParameterNameException(parameterName);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cant access field " + parameterName, e.getCause());
            }
        });
    }
}