package com.purewash.services.Auth;

import com.purewash.models.User;
import com.purewash.requests.Auth.UserRegisterDTO;

public interface IAuthService {
    User createUser(UserRegisterDTO userRegisterDTO) throws Exception;
}
