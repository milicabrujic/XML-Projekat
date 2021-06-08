package com.main.app.converter.user;

import com.main.app.domain.dto.user.UserDTO;
import com.main.app.domain.model.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {

    public static User userDTOtoUserEntity(UserDTO userDTO){
        return User
                .builder()
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .address(userDTO.getAddress())
                .password(userDTO.getPassword())
                .phoneNumber(userDTO.getPhoneNumber())
                .city(userDTO.getCity())
                .country(userDTO.getCountry())
                .build();
    }

    public static UserDTO userEntityToUserDTO(User user){
        return UserDTO
                .builder()
                .firstName(user.getLastName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .address(user.getAddress())
                .city(user.getCity())
                .country(user.getCountry())
                .role(user.getRole())
                .build();
    }

    public static List<UserDTO> usersListToUsersDTOList(List<User> users) {
        return users
                .stream()
                .map(user -> userEntityToUserDTO(user))
                .collect(Collectors.toList());
    }
}
