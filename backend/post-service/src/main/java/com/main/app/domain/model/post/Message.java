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
public class Message extends AbstractEntity {

    @Column(name = "user_from")
    private Long userFrom;

    @Column(name = "user_to")
    private Long userTo;

    @Column(name = "type")
    private String type;

    @Column(name = "content")
    private String content;
}
