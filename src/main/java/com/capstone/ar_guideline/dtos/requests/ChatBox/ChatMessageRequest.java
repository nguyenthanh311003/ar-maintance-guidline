  package com.capstone.ar_guideline.dtos.requests.ChatBox;

  import java.util.UUID;

  import com.capstone.ar_guideline.dtos.requests.RequestRevision.RequestRevisionRequest;
  import lombok.AllArgsConstructor;
  import lombok.Builder;
  import lombok.Data;
  import lombok.NoArgsConstructor;
  import org.springframework.web.bind.annotation.ModelAttribute;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public class ChatMessageRequest {
    private UUID userId;
    private String content;
    private UUID chatBoxId;
    private String companyRequestId;

    private RequestRevisionRequest requestRevision;
  }
