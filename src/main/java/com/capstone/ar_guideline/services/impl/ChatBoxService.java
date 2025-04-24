package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.ChatBox.ChatMessageRequest;
import com.capstone.ar_guideline.dtos.responses.ChatMessage.ChatMessageResponse;
import com.capstone.ar_guideline.entities.*;
import com.capstone.ar_guideline.repositories.ChatBoxRepository;
import com.capstone.ar_guideline.repositories.ChatMessageRepository;
import com.capstone.ar_guideline.repositories.CompanyRequestRepository;
import com.capstone.ar_guideline.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatBoxService {
  private final ChatBoxRepository chatBoxRepository;
  private final ChatMessageRepository chatMessageRepository;
  private final UserRepository userRepository;
  private final CompanyRequestRepository companyRequestRepository;

  @Transactional
  public ChatBox createChatBox(List<UUID> participants, UUID companyRequestId) {
    ChatBox chatBox = new ChatBox();
    Optional<CompanyRequest> companyRequest =
        Optional.ofNullable(
            companyRequestRepository.findByRequestId(String.valueOf(companyRequestId)));
    chatBox.setCompanyRequest(companyRequest.get());
    for (UUID userId : participants) {
      User user = userRepository.findUserById(String.valueOf(userId));
      ChatBoxUser chatBoxUser = new ChatBoxUser();
      chatBoxUser.setChatBox(chatBox);
      chatBoxUser.setUser(user);
      chatBox.getParticipants().add(chatBoxUser);
    }

    return chatBoxRepository.save(chatBox);
  }

  @Transactional
  public ChatMessage addMessageToChatBox(ChatMessageRequest request) {
    CompanyRequest companyRequest =
        companyRequestRepository.findByRequestId(String.valueOf(request.getChatBoxId()));
    ChatBox chatBox =
        chatBoxRepository
            .findById(companyRequest.getChatBoxes().get(0).getId())
            .orElseThrow(() -> new RuntimeException("Chat box not found"));
    User user = userRepository.findUserById(String.valueOf(request.getUserId()));
    ChatMessage message = new ChatMessage();
    message.setChatBox(chatBox);
    message.setContent(request.getContent());
    message.setUser(user);
    chatMessageRepository.save(message);
    return message;
  }

  @Transactional(readOnly = true)
  public List<ChatMessageResponse> getChatBoxMessages(UUID chatBoxId) {
    CompanyRequest companyRequest =
        companyRequestRepository.findByRequestId(String.valueOf(chatBoxId));
    List<ChatMessage> messages =
        chatMessageRepository.findByChatBoxIdOrderByTimestampAsc(
            companyRequest.getChatBoxes().get(0).getId());
    return messages.stream()
        .map(
            message ->
                ChatMessageResponse.builder()
                    .content(message.getContent())
                    .senderEmail(message.getUser().getEmail())
                    .timestamp(message.getTimestamp().toString())
                    .build())
        .collect(Collectors.toList());
  }

  //    @Transactional(readOnly = true)
  //    public List<ChatBox> getUserChatBoxes(String username) {
  //        return chatBoxRepository.findByParticipantsContaining(username);
  //    }
}
