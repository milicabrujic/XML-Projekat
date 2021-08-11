package com.main.app.service.user;

import com.main.app.domain.dto.user.VerificationRequestDTO;
import com.main.app.domain.model.user.VerificationRequest;

import java.util.List;

public interface VerificationRequestService {

    void add(VerificationRequestDTO verificationRequestDTO);
    void accept(Long id);
    void decline(Long id);
    List<VerificationRequest> getPending();
}
