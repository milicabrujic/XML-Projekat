package com.main.app.controller.user;

import com.main.app.domain.dto.user.FollowDTO;
import com.main.app.domain.dto.user.UserRestrictionDTO;
import com.main.app.domain.model.user.UserRestriction;
import com.main.app.service.user.UserRestrictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-restrictions")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRestrictionController {

    @Autowired
    private UserRestrictionService userRestrictionService;

    @PostMapping
    public void restrict(@RequestBody UserRestrictionDTO userRestrictionDTO) {
        userRestrictionService.restrict(userRestrictionDTO);
    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<UserRestriction> get(@PathVariable Long id) {
        return new ResponseEntity<>(userRestrictionService.get(id), HttpStatus.OK);
    }
}
