package com.capstone.ar_guideline.dtos.requests.ChatBox;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageRequest {
  private UUID userId;
  private String content;
  private UUID chatBoxId;
}
