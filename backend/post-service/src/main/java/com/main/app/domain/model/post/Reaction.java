package com.main.app.domain.model.post;

import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.user.User;
import com.main.app.enums.ReactionType;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Reaction extends AbstractEntity {

    @ManyToOne
    private Post post;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;
}
