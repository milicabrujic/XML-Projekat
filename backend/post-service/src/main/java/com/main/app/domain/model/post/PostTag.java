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
public class PostTag extends AbstractEntity {

    @ManyToOne
    private Post post;

    @ManyToOne
    private Tag tag;
}
