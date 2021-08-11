package com.main.app.controller.user;

import com.main.app.domain.dto.user.FollowDTO;
import com.main.app.domain.dto.user.VerificationRequestDTO;
import com.main.app.domain.model.user.Follow;
import com.main.app.domain.model.user.VerificationRequest;
import com.main.app.service.user.FollowService;
import com.main.app.service.user.VerificationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/verification-requests")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VerificationRequestController {

    @Autowired
    private VerificationRequestService verificationRequestService;

    @GetMapping(path = "/pending")
    public ResponseEntity<List<VerificationRequest>> getPending() {
        return new ResponseEntity<>(verificationRequestService.getPending(), HttpStatus.OK);
    }

    @PostMapping
    public void add(@RequestBody VerificationRequestDTO verificationRequestDTO) {
        verificationRequestService.add(verificationRequestDTO);
    }

    @PutMapping(path = "/accept/{id}")
    public void accept(@PathVariable Long id) {
        verificationRequestService.accept(id);
    }

    @PutMapping(path = "/decline/{id}")
    public void decline(@PathVariable Long id) {
        verificationRequestService.decline(id);
    }
}
