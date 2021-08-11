package com.main.app.repository.user;

import com.main.app.domain.model.user.User;
import com.main.app.domain.model.user.UserRestriction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRestrictionRepository extends JpaRepository<UserRestriction, Long> {

    Optional<UserRestriction> findByToUserId(Long userId);
}
