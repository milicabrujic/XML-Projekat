package com.main.app.domain.model.post;

import com.main.app.domain.model.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Tag extends AbstractEntity {

    @Column(name = "name")
    private String name;
}