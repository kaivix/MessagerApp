package com.kaivix.chatservice.controller;

import com.kaivix.chatservice.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/send")
    @SendTo("/topic/public")
    public ChatMessage send(ChatMessage message) {
        return message;
    }
}
