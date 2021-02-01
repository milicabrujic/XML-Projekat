package com.main.app.repository.user;

import com.main.app.domain.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository for management of the User entity.
 *
 * @author Nikola
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmailAndDeletedFalse(String email);

    User save(User user);

    Optional<User> findOneByIdAndDeletedFalse(Long id);

    List<User> findAllByDeletedFalse();

    Optional<User> findOneByResetToken(String recoveryPasswordToken);

    Optional<User> findOneByRegistrationToken(String registrationToken);

    Page<User> findAllByIdInAndDeletedFalse(List<Long> idsList, Pageable pageable);
}