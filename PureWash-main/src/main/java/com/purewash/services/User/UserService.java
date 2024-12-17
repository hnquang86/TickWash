package com.purewash.services.User;


import com.purewash.dto.User.CreateUserDTO;
import com.purewash.dto.User.DeleteUserDTO;
import com.purewash.dto.User.EditUserDTO;
import com.purewash.mapper.UserMapper;
import com.purewash.models.Enums.RoleEnum;
import com.purewash.models.Role;
import com.purewash.models.User;
import com.purewash.repositories.RoleRepository;
import com.purewash.repositories.UserRepository;
import com.purewash.responses.APIResponse;
import com.purewash.responses.User.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public APIResponse<List<User>> getAllUsers() {
//        APIResponse<List<User>> apiResponse = new APIResponse<>(userRepository.findAll(), "API");
        return new APIResponse<>(userRepository.findAll(), "API");
    }

    @Override
    public APIResponse<User> createUser(CreateUserDTO createUserDTO) {
        if(userRepository.existsByEmail(createUserDTO.getEmail())){
            return new APIResponse<>(null, "Email already exists");
        }
        if(userRepository.existsByPhone(createUserDTO.getPhone())){
            return new APIResponse<>(null, "Phone already exists");
        }
        User user = userMapper.fromCreateUserDTO(createUserDTO);

        user.setRole(RoleEnum.BASIC);
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        userRepository.save(user);
        return new APIResponse<>(user, "User created");
    }

    @Override
    public APIResponse<UserResponse> editUser(EditUserDTO editUserDTO) {
        User user = userRepository.findById(editUserDTO.getId()).
                orElseThrow(() -> new RuntimeException("User does not exist"));
        Role role = roleRepository.findById(UUID.fromString(editUserDTO.getRole())).get();

        User user1 = userMapper.toUserEditDTO(editUserDTO);
        user.setRole(RoleEnum.ADMIN);
        user.setPassword(passwordEncoder.encode(editUserDTO.getPassword()));
        userRepository.save(user);

        UserResponse userResponse = userMapper.toUser_Response(user1);
        return new APIResponse<>(userResponse, "Edit Success");
        
    }

    @Override
    public APIResponse<Boolean> deleteUser(String userId) {
        User user = userRepository.findById(UUID.fromString(userId)).
                orElseThrow(() -> new RuntimeException("User does not exist"));
        if(user.getRole().equals(RoleEnum.ADMIN))
            return new APIResponse<>(null,"Cannot delete admin user");

        userRepository.delete(user);
        return new APIResponse<>(true, "User deleted");
    }

    @Override
    public APIResponse<Boolean> deletemultiUser(DeleteUserDTO deleteUserDTO) {
        List<String> listUser = deleteUserDTO.getUserId();

        listUser.forEach(item-> {
            userRepository.deleteById(UUID.fromString(item));
        });
        return new APIResponse<>(true, "User deleted");
    }


}
