package com.main.app.service.user;

import com.main.app.domain.dto.user.VerificationRequestDTO;
import com.main.app.domain.model.user.User;
import com.main.app.domain.model.user.VerificationRequest;
import com.main.app.domain.model.user.VerificationRequestStatus;
import com.main.app.repository.user.VerificationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerificationRequestServiceImpl implements VerificationRequestService {

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private VerificationRequestRepository verificationRequestRepository;


    @Override
    public void add(VerificationRequestDTO verificationRequestDTO) {

        User user = currentUserService.getCurrentUser().get();

        VerificationRequest verificationRequest = new VerificationRequest();
        verificationRequest.setCategory(verificationRequestDTO.getCategory());
        verificationRequest.setFirstName(verificationRequestDTO.getFirstName());
        verificationRequest.setLastName(verificationRequestDTO.getLastName());
        verificationRequest.setDocument(verificationRequestDTO.getDocument());
        verificationRequest.setStatus(VerificationRequestStatus.PENDING);
        verificationRequest.setUser(user);

        verificationRequestRepository.save(verificationRequest);
    }

    @Override
    public void accept(Long id) {

        VerificationRequest verificationRequest = verificationRequestRepository.getOne(id);
        verificationRequest.setStatus(VerificationRequestStatus.ACCEPTED);

        verificationRequestRepository.save(verificationRequest);
    }

    @Override
    public void decline(Long id) {
        VerificationRequest verificationRequest = verificationRequestRepository.getOne(id);
        verificationRequest.setStatus(VerificationRequestStatus.REJECTED);

        verificationRequestRepository.save(verificationRequest);
    }

    @Override
    public List<VerificationRequest> getPending() {
        return verificationRequestRepository.getVerificationRequestByStatus(VerificationRequestStatus.PENDING);
    }
}
