package com.main.app.service.user;

import com.main.app.domain.dto.user.FollowDTO;
import com.main.app.domain.model.user.Follow;

import java.util.List;

public interface FollowService {

    void follow(Long userId);
    void accept(Long userId);
    void decline(Long userId);
    List<Follow> getPending();
}
