package com.clearsolutions.testassignment.web.util;

import com.clearsolutions.testassignment.persistence.model.User;
import com.clearsolutions.testassignment.web.dto.UserDataDto;
import com.clearsolutions.testassignment.web.dto.UserDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void updateUserFromDto(UserDataDto userDataDto, @MappingTarget User user);

    UserDto convertToDto(User user);

    UserDataDto convertToUserDataDto(User user);
}