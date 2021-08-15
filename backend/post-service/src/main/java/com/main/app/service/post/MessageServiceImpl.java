package com.main.app.service.post;

import com.main.app.domain.dto.post.MessageDTO;
import com.main.app.domain.model.post.Message;
import com.main.app.domain.model.user.User;
import com.main.app.repository.post.MessageRepository;
import com.main.app.service.user.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private CurrentUserService currentUserService;

    @Override
    public void create(MessageDTO messageDTO) {

        Message message = new Message();
        message.setUserTo(messageDTO.getUserTo());
        message.setUserFrom(messageDTO.getUserFrom());
        message.setType(messageDTO.getType());
        message.setContent(messageDTO.getContent());;

        messageRepository.save(message);
    }

    @Override
    public List<Message> get() {

        User user = currentUserService.getCurrentUser();

        return messageRepository.findAllByUserTo(user.getId());
    }
}
