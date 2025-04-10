package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.dtos.requests.ChatBox.ChatMessageRequest;
import com.capstone.ar_guideline.dtos.responses.ChatMessage.ChatMessageResponse;
import com.capstone.ar_guideline.entities.ChatBox;
import com.capstone.ar_guideline.entities.ChatMessage;
import com.capstone.ar_guideline.services.impl.ChatBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chat-boxes")
@RequiredArgsConstructor
public class ChatBoxController {
    private final ChatBoxService chatBoxService;

    @MessageMapping("/chat/{chatBoxId}")
    @SendTo("/topic/chat/{chatBoxId}")
    public ChatMessageResponse sendMessage(
            @DestinationVariable String chatBoxId,
            @Payload ChatMessageRequest messageRequest
    ) {
        // Process and save the message
        ChatMessage savedMessage = chatBoxService.addMessageToChatBox(messageRequest);

        return ChatMessageResponse.builder()
                .content(savedMessage.getContent())
                .senderEmail(savedMessage.getUser().getEmail())
                .timestamp(savedMessage.getTimestamp().toString())
                .build();

    }

    @GetMapping("/{chatBoxId}/messages")
    public ResponseEntity<List<ChatMessageResponse>> getChatBoxMessages(@PathVariable UUID chatBoxId) {
        List<ChatMessageResponse> messages = chatBoxService.getChatBoxMessages(chatBoxId);
        return ResponseEntity.ok(messages);
    }
}
