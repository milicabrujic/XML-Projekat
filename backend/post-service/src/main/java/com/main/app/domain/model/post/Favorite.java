package com.main.app.domain.model.post;

import com.main.app.domain.model.AbstractEntity;
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
public class Favorite extends AbstractEntity {

    @ManyToOne
    private Post post;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "favorite")
    private boolean favorite;
}
