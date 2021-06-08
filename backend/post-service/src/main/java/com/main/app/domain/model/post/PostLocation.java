package com.main.app.domain.model.post;

import com.main.app.domain.model.AbstractEntity;
import lombok.*;

import javax.persistence.ManyToOne;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostLocation extends AbstractEntity {

    @ManyToOne
    private Post post;

    @ManyToOne
    private Location location;
}
