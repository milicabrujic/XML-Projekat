package com.main.app.service.user;

import com.main.app.domain.dto.user.UserRestrictionDTO;
import com.main.app.domain.model.user.RestrictionType;
import com.main.app.domain.model.user.User;
import com.main.app.domain.model.user.UserRestriction;
import com.main.app.repository.user.UserRepository;
import com.main.app.repository.user.UserRestrictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRestrictionServiceImpl implements UserRestrictionService {

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRestrictionRepository userRestrictionRepository;

    @Override
    public void restrict(UserRestrictionDTO userRestrictionDTO) {

        User user = currentUserService.getCurrentUser().get();

        UserRestriction userRestriction = new UserRestriction();
        userRestriction.setUser(user);
        userRestriction.setToUser(userRepository.getOne(userRestrictionDTO.getUserId()));

        if(userRestrictionDTO.getRestrictionType().equals("MUTE"))
        {
            userRestriction.setRestrictionType(RestrictionType.MUTE);
        }
        else {
            userRestriction.setRestrictionType(RestrictionType.BLOCK);
        }

        userRestrictionRepository.save(userRestriction);

    }

    @Override
    public UserRestriction get(Long userId) {
        return userRestrictionRepository.findByToUserId(userId).get();
    }
}
