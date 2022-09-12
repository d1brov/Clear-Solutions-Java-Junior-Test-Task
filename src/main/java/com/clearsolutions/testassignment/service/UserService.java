package com.clearsolutions.testassignment.service;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.persistence.repository.UserRepository;
import com.clearsolutions.testassignment.web.dto.UserRegistrationDto;
import com.clearsolutions.testassignment.web.exception.InvalidParametersException;
import com.clearsolutions.testassignment.web.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${validation.user.age.minimum.years}")
    private int minimumUserAgeYears;

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
            throw new UserAlreadyExistsException(
                    String.format("User with same phone: '%s' already exists", phone));
        }

        LocalDate birthDate = userRegistrationDto.getBirthDate();
        // minimum user age check
        int userAgeYears = Period.between(birthDate, LocalDate.now()).getYears();
        if(userAgeYears < minimumUserAgeYears) {
            throw new InvalidParametersException(
                    String.format("User have to be at least %d years old", minimumUserAgeYears));
        }

        User userToSave = new User(
                null,
                email,
                userRegistrationDto.getFirstName(),
                userRegistrationDto.getLastName(),
                birthDate,
                phone,
                userRegistrationDto.getAddress()
        );
        return userRepository.save(userToSave);
    }
}
