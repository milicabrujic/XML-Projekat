package com.main.app.service.user;

import com.main.app.converter.user.UserConverter;
import com.main.app.domain.dto.user.UserDTO;
import com.main.app.domain.model.user.User;

import com.main.app.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;
import com.main.app.enums.Role;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(UserDTO userDTO, Role role) {

        User user = UserConverter.userDTOtoUserEntity(userDTO);
        user.setRole(role);

        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) {

        Optional<User> userDb = userRepository.findOneById(id);

        if(!userDb.isPresent()) {
            return null;
        }

        User user = userDb.get();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAddress(userDTO.getAddress());
        user.setCity(userDTO.getCity());
        user.setCountry(userDTO.getCountry());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAllowMessages(userDTO.isAllowMessages());
        user.setAllowTags(userDTO.isAllowTags());
        user.setPrivateProfile(userDTO.isPrivateProfile());
        user.setAllowNotification(userDTO.isAllowNotification());
        userRepository.save(user);

        return user;
    }
}

