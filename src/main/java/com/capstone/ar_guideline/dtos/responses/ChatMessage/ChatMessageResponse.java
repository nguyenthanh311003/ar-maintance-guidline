package com.capstone.ar_guideline.dtos.responses.ChatMessage;

import com.capstone.ar_guideline.dtos.requests.RequestRevision.RequestRevisionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageResponse {
  private String id;
  private String content;
  private String senderEmail;
  private String timestamp;
  private RequestRevisionResponse requestRevisionResponse;
}
