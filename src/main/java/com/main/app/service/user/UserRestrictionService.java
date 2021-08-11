package com.main.app.service.user;

import com.main.app.domain.dto.user.UserRestrictionDTO;
import com.main.app.domain.model.user.UserRestriction;

public interface UserRestrictionService {
    void restrict(UserRestrictionDTO userRestrictionDTO);
    UserRestriction get(Long userId);
}
