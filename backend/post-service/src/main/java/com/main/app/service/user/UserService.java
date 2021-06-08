package com.main.app.service.user;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.user.UserDTO;
import com.main.app.domain.model.user.User;
import com.main.app.enums.Role;

import java.util.Optional;

public interface UserService {

    User registerUser(UserDTO userDTO, Role role);
    User updateUser(Long id, UserDTO userDTO);
}
