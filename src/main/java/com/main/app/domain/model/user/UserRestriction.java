package com.main.app.domain.model.user;

import com.main.app.domain.model.AbstractEntity;
import com.main.app.enums.Gender;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserRestriction extends AbstractEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private User toUser;

    @Column(name = "status")
    private boolean status;

    @Enumerated(EnumType.STRING)
    private RestrictionType restrictionType;
}
