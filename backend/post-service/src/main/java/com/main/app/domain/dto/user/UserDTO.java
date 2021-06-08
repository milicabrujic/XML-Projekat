package com.main.app.domain.dto.user;


import com.main.app.enums.Role;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String city;
    private String country;
    private String phoneNumber;
    private Role role;
}
