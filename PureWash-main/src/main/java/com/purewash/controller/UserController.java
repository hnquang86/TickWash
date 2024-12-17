package com.purewash.controller;


import com.purewash.dto.User.CreateUserDTO;
import com.purewash.dto.User.DeleteUserDTO;
import com.purewash.dto.User.EditUserDTO;
import com.purewash.models.User;
import com.purewash.repositories.UserRepository;
import com.purewash.responses.APIResponse;
import com.purewash.responses.User.UserResponse;
import com.purewash.constants.Endpoint;
import com.purewash.services.User.UserService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoint.User.BASE)
public class UserController {
    private final UserService userService;

    @GetMapping("/getuser")
    public ResponseEntity<?> getUser() {
        try {
            APIResponse<List<User>> response = userService.getAllUsers();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createuser")
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO createUserDTO) {
        return ResponseEntity.ok(userService.createUser(createUserDTO));
    }

    @PutMapping("/updateuser")
    public ResponseEntity<APIResponse<UserResponse>> editUser(@RequestBody EditUserDTO edit) {
        return ResponseEntity.ok(userService.editUser(edit));
    }

    @DeleteMapping("/deleteByID")
    public ResponseEntity<APIResponse<Boolean>> deleteUser(@PathVariable String userID) {
        return ResponseEntity.ok(userService.deleteUser(userID));
    }

    @DeleteMapping("/deleteManyUser")
    public ResponseEntity<APIResponse<Boolean>> deleteManyUser(@RequestBody DeleteUserDTO deleteUserDTO) {
        return ResponseEntity.ok(userService.deletemultiUser(deleteUserDTO));

    }
}
