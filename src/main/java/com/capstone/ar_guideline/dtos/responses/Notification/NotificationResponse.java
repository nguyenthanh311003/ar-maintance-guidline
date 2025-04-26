package com.capstone.ar_guideline.dtos.responses.Notification;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {
    private String id;
    private String title;
    private String content;
    private String type;
    private String key;
    private String status;
    private String userId;
    private String createdAt;
    private String updatedAt;
}
