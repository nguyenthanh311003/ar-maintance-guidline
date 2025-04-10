package com.capstone.ar_guideline.dtos.responses.ChatMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageResponse {
  private String content;
  private String senderEmail;
  private String timestamp;
}
