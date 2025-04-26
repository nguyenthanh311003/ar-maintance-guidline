package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.Notification.NotificationRequest;
import com.capstone.ar_guideline.dtos.responses.Notification.NotificationResponse;
import com.capstone.ar_guideline.entities.Notification;
import com.capstone.ar_guideline.entities.User;
import com.capstone.ar_guideline.repositories.NotificationRepository;
import com.capstone.ar_guideline.repositories.UserRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

  private final NotificationRepository notificationRepository;
  @Autowired private UserRepository userRepository;

  public NotificationResponse create(NotificationRequest request) {
    Notification notification = new Notification();
    notification.setTitle(request.getTitle());
    notification.setContent(request.getContent());
    notification.setType(request.getType());
    notification.setKeyValue(request.getKey());
    notification.setStatus(request.getStatus());

    User user = userRepository.findUserById(request.getUserId());

    notification.setUser(user);

    Notification savedNotification = notificationRepository.save(notification);
    return mapToResponse(savedNotification);
  }

  public List<NotificationResponse> getAllByUserId(UUID userId) {
    return notificationRepository.findAllByUserId(String.valueOf(userId)).stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  public NotificationResponse changeStatus(UUID notificationId, String status) {
    Notification notification =
        notificationRepository
            .findById(notificationId)
            .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
    notification.setStatus(status);
    Notification updatedNotification = notificationRepository.save(notification);
    return mapToResponse(updatedNotification);
  }

  private NotificationResponse mapToResponse(Notification notification) {
    return NotificationResponse.builder()
        .id(notification.getId().toString())
        .title(notification.getTitle())
        .content(notification.getContent())
        .type(notification.getType())
        .key(notification.getKeyValue())
        .status(notification.getStatus())
        .userId(notification.getUser().getId().toString())
        .createdDate(notification.getCreatedDate())
        .updatedDate(notification.getUpdatedDate())
        .build();
  }
}
