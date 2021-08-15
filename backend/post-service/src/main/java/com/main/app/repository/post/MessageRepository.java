package com.main.app.repository.post;

import com.main.app.domain.model.post.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByUserTo(Long userId);
}
