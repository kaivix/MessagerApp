package com.kaivix.chatservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("messages")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChatMessage {

    private UUID id;
    private String sender;
    private String receiver;
    private String content;
    private long timestamp;

}
