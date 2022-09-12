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
        // user with duplicate email check
        if(userRepository.findByEmail(email) != null) {
            throw new UserAlreadyExistsException(String.format("User with same email: '%s' already exists", email));
        }

        String phone = userRegistrationDto.getPhone();
        // user with duplicate phone check
        if(phone != null && userRepository.findByPhone(phone) != null) {
            throw new UserAlreadyExistsException(String.format("User with same phone: '%s' already exists", phone));
        }

        User userToSave = new User(
                null,
                email,
                userRegistrationDto.getFirstName(),
                userRegistrationDto.getLastName(),
                userRegistrationDto.getBirthDate(),
                phone,
                userRegistrationDto.getAddress()
        );
        return userRepository.save(userToSave);
    }
}
