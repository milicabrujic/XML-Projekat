package com.main.app.controller.post;

import com.main.app.domain.dto.post.MessageDTO;
import com.main.app.domain.model.post.Message;
import com.main.app.service.post.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public void create(@RequestBody MessageDTO messageDTO) {
        messageService.create(messageDTO);
    }

    @GetMapping
    public ResponseEntity<List<Message>> get() {
        return new ResponseEntity<>(messageService.get(), HttpStatus.OK);
    }
}
