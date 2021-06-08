package com.main.app.service.post;

import com.main.app.domain.model.post.Location;
import com.main.app.domain.model.post.Tag;

public interface LocationService {

    Location addLocation(String name, Long postId);
}
