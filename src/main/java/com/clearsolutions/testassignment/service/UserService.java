package com.clearsolutions.testassignment.service;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.persistence.repository.UserRepository;
import com.clearsolutions.testassignment.web.dto.DateRangeDto;
import com.clearsolutions.testassignment.web.dto.UserDataDto;
import com.clearsolutions.testassignment.web.exception.UserWithIdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    public User create(UserDataDto userDataDto) {
        User userToSave = modelMapper.map(userDataDto, User.class); // todo mapping ?
        return userRepository.save(userToSave);
    }

    public List<User> findInBirthdateRange(DateRangeDto dateRange) {
        Set<ConstraintViolation<DateRangeDto>> constraintViolations = validator.validate(dateRange);
        if(constraintViolations.size() > 0) {
            throw new ConstraintViolationException(constraintViolations);
        }
        return userRepository.findInBirthdateRange(dateRange.getFrom(), dateRange.getTo());
    }

    public User delete(Integer id) {
        User deletedUser = userRepository.findById(id).orElseThrow(
                () -> new UserWithIdNotFoundException(id));
        userRepository.delete(deletedUser);
        return deletedUser;
    }
}