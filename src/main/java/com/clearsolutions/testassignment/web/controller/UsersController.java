package com.clearsolutions.testassignment.web.controller;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.service.UsersServiceImpl;
import com.clearsolutions.testassignment.web.dto.UserDto;
import com.clearsolutions.testassignment.web.dto.UserDataDto;
import com.clearsolutions.testassignment.web.util.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("users")
public class UsersController {
    private final UsersServiceImpl usersService;
    private final UserMapper userMapper;

    @Autowired
    public UsersController(UsersServiceImpl usersService) {
        this.usersService = usersService;
        this.userMapper = Mappers.getMapper(UserMapper.class);
    }

    @PostMapping
    ResponseEntity<UserDto> create(@RequestBody UserDataDto userDataDto) {
        User createdUser = usersService.create(userDataDto);
        UserDto createdUserDto = userMapper.convertToDto(createdUser);
        return new ResponseEntity<>(createdUserDto, CREATED);
    }

    @GetMapping("/birthdate")
    ResponseEntity<List<UserDto>> findByBirthdateRange(@RequestParam LocalDate from,
                                                       @RequestParam LocalDate to) {
        List<UserDto> usersInRange = usersService
                .findInBirthdateRange(from, to)
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(usersInRange, OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<UserDto> update(@PathVariable Integer id,
                                   @RequestBody UserDataDto updatedUserDataDto) {
        User updatedUser = usersService.update(id, updatedUserDataDto);
        UserDto updatedUserDto = userMapper.convertToDto(updatedUser);
        return new ResponseEntity<>(updatedUserDto, OK);
    }

    @PatchMapping("/{id}")
    ResponseEntity<UserDto> partialUpdate(@PathVariable Integer id,
                                          @RequestBody Map<String, String> parameters) {
        User patchedUser = usersService.partialUpdate(id, parameters);
        UserDto updatedUserDto = userMapper.convertToDto(patchedUser);
        return new ResponseEntity<>(updatedUserDto, OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<UserDto> deleteById(@PathVariable Integer id) {
        User deletedUser = usersService.delete(id);
        UserDto deletedUserDto = userMapper.convertToDto(deletedUser);
        return new ResponseEntity<>(deletedUserDto, OK);
    }
}