package com.main.app.repository.user;

import com.main.app.domain.model.user.Follow;
import com.main.app.domain.model.user.VerificationRequest;
import com.main.app.domain.model.user.VerificationRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VerificationRequestRepository extends JpaRepository<VerificationRequest, Long> {

    List<VerificationRequest> getVerificationRequestByStatus(VerificationRequestStatus status);
}
