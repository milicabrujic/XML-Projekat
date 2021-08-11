package com.main.app.controller.user;

import com.main.app.domain.dto.user.FollowDTO;
import com.main.app.domain.dto.user.UserDTO;
import com.main.app.domain.model.user.Follow;
import com.main.app.service.user.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FollowController {

    @Autowired
    private FollowService followService;

    @GetMapping(path = "/pending")
    public ResponseEntity<List<Follow>> getPending() {
        return new ResponseEntity<>(followService.getPending(), HttpStatus.OK);
    }

    @PostMapping
    public void follow(@RequestBody FollowDTO followDTO) {
        followService.follow(followDTO.getUserId());
    }

    @PutMapping(path = "/accept/{id}")
    public void accept(@PathVariable Long id) {
        followService.accept(id);
    }

    @PutMapping(path = "/decline/{id}")
    public void decline(@PathVariable Long id) {
        followService.decline(id);
    }
}
