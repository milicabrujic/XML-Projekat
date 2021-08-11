package com.main.app.repository.user;

import com.main.app.domain.model.user.Follow;
import com.main.app.domain.model.user.FollowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findAllByUserIdAndBySAndStatus(Long userId, FollowStatus status);
}
