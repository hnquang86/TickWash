package com.purewash.mapper;

import com.purewash.dto.User.CreateUserDTO;
import com.purewash.dto.User.EditUserDTO;
import com.purewash.models.User;
import com.purewash.requests.UserLoginDTO;
import com.purewash.responses.Auth.UserLoginResponse;
import com.purewash.responses.User.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserLoginDTO request);
    User toUserEditDTO(EditUserDTO editUserDTO);

    UserLoginResponse toUserResponse(User user);
    UserResponse toUser_Response(User user);
    EditUserDTO toEditUserDTO(User user);
    User fromCreateUserDTO(CreateUserDTO createUserDTO);


    @Mapping(target = "role", ignore = true)
    void updateUser(@MappingTarget User user, UserLoginResponse userLoginResponse);
}
