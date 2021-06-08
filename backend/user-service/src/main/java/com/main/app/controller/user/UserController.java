package com.main.app.controller.user;

import com.main.app.converter.user.UserConverter;
import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.user.*;
import com.main.app.domain.model.user.User;
import com.main.app.enums.Role;
import com.main.app.service.user.CurrentUserService;
import com.main.app.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    private final CurrentUserService currentUserService;

    @GetMapping(path = "/current")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return new ResponseEntity<>(currentUserService.getCurrentUserDTO(), HttpStatus.OK);
    }

    @PostMapping(path = "/register/user")
    public ResponseEntity<UserDTO> userRegister(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(UserConverter.userEntityToUserDTO(userService.registerUser(userDTO, Role.USER)), HttpStatus.OK);
    }

    @PostMapping(path = "/register/admin")
    public ResponseEntity<UserDTO> adminRegister(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(UserConverter.userEntityToUserDTO(userService.registerUser(userDTO, Role.USER)), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserDTO> editUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(UserConverter.userEntityToUserDTO(userService.updateUser(id, userDTO)), HttpStatus.OK);
    }

}
