package com.capstone.ar_guideline.dtos.requests.Notification;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequest {

  private String title;
  private String content;
  private String type;
  private String key;
  private String status;
  private String userId;

  // Constructors, getters, and setters
}
