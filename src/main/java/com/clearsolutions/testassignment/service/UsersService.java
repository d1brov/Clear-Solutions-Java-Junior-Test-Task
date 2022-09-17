package com.clearsolutions.testassignment.service;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.persistence.repository.UserRepository;
import com.clearsolutions.testassignment.validation.order.ValidationGroupSequence;
import com.clearsolutions.testassignment.web.dto.DateRangeDto;
import com.clearsolutions.testassignment.web.exception.InvalidParameterNameException;
import com.clearsolutions.testassignment.web.util.UserMapper;
import com.clearsolutions.testassignment.web.dto.UserDataDto;
import com.clearsolutions.testassignment.web.exception.UserWithIdNotFoundException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
        User userToSave = userMapper.convertToUser(userDataDto);
        validate(userToSave);
        return userRepository.save(userToSave);
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

    public List<User> findInBirthdateRange(LocalDate from, LocalDate to) {
        validate(new DateRangeDto(from, to));
        return userRepository.findInBirthdateRange(from, to);
    }

    public User partialUpdate(Integer id, Map<String, String> parameters) {
        User updatingUser = userRepository.findById(id).orElseThrow(
                () -> new UserWithIdNotFoundException(id));

        parameters.forEach((parameterName, parameterValue) -> {
            try {
                Field field = User.class.getDeclaredField(parameterName);
                field.setAccessible(true);
                field.set(updatingUser, parameterValue);
            } catch (NoSuchFieldException | IllegalArgumentException e) {
                throw new InvalidParameterNameException(parameterName);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cant access field " + parameterName, e.getCause());
            }
        });

        validate(updatingUser);
        return userRepository.save(updatingUser);
    }

    public User update(Integer id, UserDataDto userDataDto) {
        userRepository.findById(id).orElseThrow(
                () -> new UserWithIdNotFoundException(id));

        User updatingUser = userMapper.convertToUser(userDataDto);
        updatingUser.setId(id);
        validate(updatingUser);
        return userRepository.save(updatingUser);
    }

    private <T> void validate(T object) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object, ValidationGroupSequence.class);
        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}