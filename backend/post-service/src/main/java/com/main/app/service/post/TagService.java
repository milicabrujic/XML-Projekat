package com.main.app.service.post;

import com.main.app.domain.model.post.Tag;

public interface TagService {

    Tag addTag(String name, Long postId);
}
