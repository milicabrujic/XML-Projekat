package com.main.app.domain.model.post;

import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.user.User;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Comment extends AbstractEntity {

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;
}
