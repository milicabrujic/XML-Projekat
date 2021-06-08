package com.main.app.domain.model.user;

import com.main.app.domain.dto.user.UserDTO;
import com.main.app.domain.model.AbstractEntity;
import com.main.app.enums.Gender;
import com.main.app.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class User extends AbstractEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "website")
    private String website;

    @Column(name = "bio")
    private String bio;

    @Column(name = "private_profile")
    private boolean privateProfile;

    @Column(name = "allow_messages")
    private boolean allowMessages;

    @Column(name = "allow_tags")
    private boolean allowTags;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public User(@NotNull String password, @NotNull String email, @NotNull String firstName, @NotNull String lastName) {
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(UserDTO userDTO) {
        this.setEmail(userDTO.getEmail());
        this.setFirstName(userDTO.getFirstName());
        this.setPassword(userDTO.getPassword());
        this.setLastName(userDTO.getLastName());
        this.setPhoneNumber(userDTO.getPhoneNumber());
        this.setRole(userDTO.getRole());
        this.setAddress(userDTO.getAddress());
    }
}