package com.clearsolutions.testassignment.web;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.service.UserService;
import com.clearsolutions.testassignment.web.dto.UserDto;
import com.clearsolutions.testassignment.web.dto.UserRegistrationDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    public UserDto createNewUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        User createdUser = userService.create(userRegistrationDto);
        return modelMapper.map(createdUser, UserDto.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public UserDto deleteUserById(@PathVariable Integer id) {  // todo add validation for positive integer (1-Integer.MAX_SIZE)
        User deletedUser = userService.delete(id);
        return modelMapper.map(deletedUser, UserDto.class);
    }
}