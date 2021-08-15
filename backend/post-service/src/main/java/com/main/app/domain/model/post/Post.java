package com.main.app.domain.model.post;

import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.album.Album;
import com.main.app.domain.model.user.User;
import com.main.app.enums.PostType;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Post extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "post_date")
    private Instant postDate;

    @Column(name = "content")
    private String content;

    @Column(name = "public_post")
    private boolean publicPost;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    private Album album;

    @Column
    private boolean higlight;
}
