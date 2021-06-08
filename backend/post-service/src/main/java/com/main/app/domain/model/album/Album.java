package com.main.app.domain.model.album;

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
public class Album extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @ManyToOne
    private User user;
}
