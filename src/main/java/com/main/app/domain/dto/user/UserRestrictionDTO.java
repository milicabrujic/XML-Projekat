package com.main.app.domain.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRestrictionDTO {

    private Long userId;
    private String restrictionType;
}
