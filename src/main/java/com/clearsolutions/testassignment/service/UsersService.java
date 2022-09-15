package com.clearsolutions.testassignment.service;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.persistence.repository.UserRepository;
import com.clearsolutions.testassignment.validation.order.ValidationGroupSequence;
import com.clearsolutions.testassignment.web.util.UserMapper;
import com.clearsolutions.testassignment.web.dto.DateRangeDto;
import com.clearsolutions.testassignment.web.dto.UserDataDto;
import com.clearsolutions.testassignment.web.exception.UserWithIdNotFoundException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.*;
import java.util.List;
import java.util.Set;

@Service
public class UsersService {
    private final UserRepository userRepository;
    private final Validator validator;
    private final UserMapper userMapper;

    @Autowired
    public UsersService(UserRepository userRepository, Validator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.userMapper = Mappers.getMapper(UserMapper.class);
    }

    public User create(UserDataDto userDataDto) {
        User userToSave = new User();
        userMapper.updateUserFromDto(userDataDto, userToSave);
        validate(userToSave);
        return userRepository.save(userToSave);
    }

    public List<User> findInBirthdateRange(DateRangeDto dateRange) {
        validate(dateRange);
        return userRepository.findInBirthdateRange(dateRange.getFrom(), dateRange.getTo());
    }

    public User update(Integer id, UserDataDto userDataDto) {
        // load updating user
        User updatingUser = userRepository.findById(id).orElseThrow(
                () -> new UserWithIdNotFoundException(id));

        // updating user from DTO
        userMapper.updateUserFromDto(userDataDto, updatingUser);

        // validating user
        validate(updatingUser);

        // updating
        return userRepository.save(updatingUser);
    }

    public User delete(Integer id) {
        // find user by ID
        User deletedUser = userRepository.findById(id).orElseThrow(
                () -> new UserWithIdNotFoundException(id));

        // delete user
        userRepository.delete(deletedUser);

        // return deleted user
        return deletedUser;
    }

    private <T> void validate(T object) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object, ValidationGroupSequence.class);
        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}