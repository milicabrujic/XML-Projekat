package com.main.app.domain.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationRequestDTO {

    private String firstName;

    private String lastName;

    private String category;

    private String document;
}
