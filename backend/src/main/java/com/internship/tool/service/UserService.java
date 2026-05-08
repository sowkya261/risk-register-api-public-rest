
package com.internship.tool.service;

import com.internship.tool.dto.RegisterRequest;
import com.internship.tool.dto.UserDto;

public interface UserService {
    UserDto registerUser(RegisterRequest request);
    UserDto getUserByEmail(String email);
}
