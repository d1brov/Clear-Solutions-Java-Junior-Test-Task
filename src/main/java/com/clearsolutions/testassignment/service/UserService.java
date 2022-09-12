package com.clearsolutions.testassignment.service;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.persistence.repository.UserRepository;
import com.clearsolutions.testassignment.web.dto.UserRegistrationDto;
import com.clearsolutions.testassignment.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public User create(UserRegistrationDto userRegistrationDto) {
        User userToSave = modelMapper.map(userRegistrationDto, User.class);
        return userRepository.save(userToSave);
    }

    public List<User> findInBirthdateRange(LocalDate fromDate, LocalDate tillDate) {
        return userRepository.findInBirthdateRange(fromDate, tillDate);
    }

    public User delete(Integer id) {
        User deletedUser = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("User with id: %d not found", id)));

        userRepository.delete(deletedUser);

        return deletedUser;
    }
}