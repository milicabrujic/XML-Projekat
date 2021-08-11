package com.main.app.service.user;

import com.main.app.domain.model.user.Follow;
import com.main.app.domain.model.user.FollowStatus;
import com.main.app.domain.model.user.User;
import com.main.app.repository.user.FollowRepository;
import com.main.app.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Override
    public void follow(Long userId) {

        Optional<User> user = currentUserService.getCurrentUser();

        if(!user.isPresent()) {
            return;
        }

        User toFollow = userRepository.getOne(userId);

        Follow follow = new Follow();
        follow.setUser(user.get());
        follow.setFollowing(toFollow);

        if(user.get().isPrivateProfile()) {
            follow.setStatus(FollowStatus.PENDING);
        }
        else {
            follow.setStatus(FollowStatus.APPROVED);
        }

        followRepository.save(follow);
    }

    @Override
    public void accept(Long id) {

        Follow follow = followRepository.getOne(id);

        follow.setStatus(FollowStatus.APPROVED);
        followRepository.save(follow);
    }

    @Override
    public void decline(Long id) {

        Follow follow = followRepository.getOne(id);

        follow.setStatus(FollowStatus.REJECTED);
        followRepository.save(follow);
    }

    @Override
    public List<Follow> getPending() {

        User user = currentUserService.getCurrentUser().get();

        return followRepository.findAllByUserIdAndBySAndStatus(user.getId(), FollowStatus.PENDING);
    }
}
