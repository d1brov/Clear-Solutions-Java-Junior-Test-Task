package com.clearsolutions.testassignment.web.controller;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.service.UsersService;
import com.clearsolutions.testassignment.web.dto.DateRangeDto;
import com.clearsolutions.testassignment.web.dto.UserDto;
import com.clearsolutions.testassignment.web.dto.UserDataDto;
import com.clearsolutions.testassignment.web.util.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("users")
public class UsersController {
    private final UsersService usersService;
    private final UserMapper userMapper;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
        this.userMapper = Mappers.getMapper(UserMapper.class);
    }

    @PostMapping
    ResponseEntity<UserDto> create(@RequestBody UserDataDto userDataDto) {
        User createdUser = usersService.create(userDataDto);
        UserDto createdUserDto = userMapper.convertToDto(createdUser);
        return new ResponseEntity<>(createdUserDto, CREATED);
    }

    //GET /users/birthdate?from=1895-01-01&to=2000-01-01
    @GetMapping("/birthdate")
    ResponseEntity<List<UserDto>> findByRange(DateRangeDto dateRange) {
        List<UserDto> usersInRange = usersService
                .findInBirthdateRange(dateRange)
                .stream()
                .map(user -> userMapper.convertToDto(user))
                .collect(Collectors.toList());
        return new ResponseEntity<>(usersInRange, OK);
    }

    @PutMapping("/{id}/update")
    ResponseEntity<UserDto> update(@PathVariable Integer id,
                                   @RequestBody UserDataDto updatedUserDataDto) {
        User updatedUser = usersService.update(id, updatedUserDataDto);
        UserDto updatedUserDto = userMapper.convertToDto(updatedUser);
        return new ResponseEntity<>(updatedUserDto, OK);
    }

    @PutMapping("/{id}/update/parameters")
    ResponseEntity<UserDto> updateParameters(@PathVariable Integer id,
                                             @RequestParam Map<String, String> parameters) {
        UserDataDto userDataDto = new UserDataDto(parameters);
        User updatedUser = usersService.update(id, userDataDto);
        UserDto updatedUserDto = userMapper.convertToDto(updatedUser);
        return new ResponseEntity<>(updatedUserDto, OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<UserDto> deleteById(@PathVariable Integer id) {
        User deletedUser = usersService.delete(id);
        UserDto deletedUserDto = userMapper.convertToDto(deletedUser);
        return new ResponseEntity<>(deletedUserDto, OK);
    }
}