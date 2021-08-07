package com.main.app.domain.model.user;

import com.main.app.domain.model.AbstractEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Follow extends AbstractEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private User following;

    @Enumerated(EnumType.STRING)
    private FollowStatus status;
}
