package com.main.app.domain.model.post;

import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.user.User;
import com.main.app.enums.ReactionType;
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
public class Reaction extends AbstractEntity {

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;
}
