package com.clearsolutions.testassignment.web;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.service.UserService;
import com.clearsolutions.testassignment.web.dto.UserDto;
import com.clearsolutions.testassignment.web.dto.UserRegistrationDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(OK)
    public UserDto createNewUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        User createdUser = userService.create(userRegistrationDto);
        return modelMapper.map(createdUser, UserDto.class);
    }
}