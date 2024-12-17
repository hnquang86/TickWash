package com.purewash.services.User;

import com.purewash.dto.User.CreateUserDTO;
import com.purewash.dto.User.DeleteUserDTO;
import com.purewash.dto.User.EditUserDTO;
import com.purewash.models.User;
import com.purewash.responses.APIResponse;
import com.purewash.responses.User.UserResponse;

import java.util.List;

public interface IUserService {
    APIResponse<List<User>> getAllUsers();

    APIResponse<User> createUser(CreateUserDTO createUserDTO);

    APIResponse<UserResponse> editUser(EditUserDTO editUserDTO);

    APIResponse<Boolean> deleteUser(String userID);

    APIResponse<Boolean> deletemultiUser(DeleteUserDTO deleteUserDTO);
}
