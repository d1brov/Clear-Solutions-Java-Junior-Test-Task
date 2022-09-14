package com.clearsolutions.testassignment.web;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.service.UserService;
import com.clearsolutions.testassignment.web.dto.DateRangeDto;
import com.clearsolutions.testassignment.web.dto.UserDto;
import com.clearsolutions.testassignment.web.dto.UserDataDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping
    ResponseEntity<UserDto> createNewUser(@Valid @RequestBody UserDataDto userRegistrationDto) {
        User createdUser = userService.create(userRegistrationDto);
        return new ResponseEntity<>(modelMapper.map(createdUser, UserDto.class), CREATED);
    }

    //GET http://localhost:8080/users/birthdate?from=1000-01-01&to=3000-01-01
    @GetMapping("/birthdate")
    ResponseEntity<List<UserDto>> findByRange(DateRangeDto dateRange) {
        List<UserDto> usersInRange = userService
                .findInBirthdateRange(dateRange)
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(usersInRange, OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<UserDto> deleteUserById(@PathVariable Integer id) {
        User deletedUser = userService.delete(id);
        return new ResponseEntity<>(modelMapper.map(deletedUser, UserDto.class), OK);
    }
}