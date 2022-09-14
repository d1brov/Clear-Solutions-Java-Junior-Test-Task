package com.clearsolutions.testassignment.service;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.persistence.repository.UserRepository;
import com.clearsolutions.testassignment.util.UserMapper;
import com.clearsolutions.testassignment.web.dto.DateRangeDto;
import com.clearsolutions.testassignment.web.dto.UserDataDto;
import com.clearsolutions.testassignment.web.exception.InvalidParameterNameException;
import com.clearsolutions.testassignment.web.exception.UserWithIdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;
    private final UserMapper mapper;

    public User create(UserDataDto userDataDto) {
        User userToSave = new User();
        mapper.dtoToUser(userDataDto, userToSave);
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
        mapper.updateUserFromDto(userDataDto, updatingUser);

        // validating
        validate(updatingUser);

        // updating
        return userRepository.save(updatingUser);
    }

    public User updateParameters(Integer id, Map<String, String> parameters) {
        UserDataDto userDataDto = new UserDataDto();
        // set modified fields in userDataDto
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            try {
                Field field = UserDataDto.class.getDeclaredField(parameter.getKey());
                field.setAccessible(true);
                field.set(userDataDto, parameter.getValue());   // todo date not setting
            } catch (NoSuchFieldException e) {
                throw new InvalidParameterNameException(parameter.getKey());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e); // todo
            }
        }
        return update(id, userDataDto);
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
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        if(constraintViolations.size() > 0) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}