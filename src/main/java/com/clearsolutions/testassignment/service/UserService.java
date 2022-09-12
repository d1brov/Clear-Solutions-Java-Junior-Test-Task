package com.clearsolutions.testassignment.service;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.persistence.repository.UserRepository;
import com.clearsolutions.testassignment.web.dto.UserRegistrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(UserRegistrationDto userRegistrationDto) {
        User userToSave = new User(
                null,
                userRegistrationDto.getEmail(),
                userRegistrationDto.getFirstName(),
                userRegistrationDto.getLastName(),
                userRegistrationDto.getBirthDate(),
                userRegistrationDto.getPhoneNumber(),
                userRegistrationDto.getAddress()
        );
        return userRepository.save(userToSave);
    }
}
