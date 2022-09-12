package com.clearsolutions.testassignment.service;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.persistence.repository.UserRepository;
import com.clearsolutions.testassignment.web.dto.UserRegistrationDto;
import com.clearsolutions.testassignment.web.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(UserRegistrationDto userRegistrationDto) {
        String email = userRegistrationDto.getEmail();
        if(userRepository.findByEmail(email) != null) {
            throw new UserAlreadyExistsException(String.format("User with email: '%s' already exists", email));
        }

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
