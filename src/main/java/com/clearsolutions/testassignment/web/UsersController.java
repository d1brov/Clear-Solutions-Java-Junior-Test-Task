package com.clearsolutions.testassignment.web;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.service.UserService;
import com.clearsolutions.testassignment.web.dto.DateRangeDto;
import com.clearsolutions.testassignment.web.dto.UserDto;
import com.clearsolutions.testassignment.web.dto.UserRegistrationDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    @ResponseStatus(CREATED)
    UserDto createNewUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        User createdUser = userService.create(userRegistrationDto);
        return modelMapper.map(createdUser, UserDto.class);
    }

    @PostMapping("/findInBirthDateRange")
    @ResponseStatus(OK)
    List<UserDto> findByBirthDateRange(@Valid @RequestBody DateRangeDto dateRange) {
        List<User> usersInRange = userService.findInBirthdateRange(dateRange.getFromDate(), dateRange.getTillDate());
        return usersInRange.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    UserDto deleteUserById(@PathVariable Integer id) {
        User deletedUser = userService.delete(id);
        return modelMapper.map(deletedUser, UserDto.class);
    }
}