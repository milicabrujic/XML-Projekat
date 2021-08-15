package com.main.app.service.post;

import com.main.app.domain.dto.post.MessageDTO;
import com.main.app.domain.model.post.Message;

import java.util.List;

public interface MessageService {

    void create(MessageDTO messageDTO);
    List<Message> get();
}
