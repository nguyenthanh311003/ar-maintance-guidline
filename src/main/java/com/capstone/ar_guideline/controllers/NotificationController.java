package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.Devices.DeviceRegistrationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.services.IFirebaseNotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationController {
  IFirebaseNotificationService firebaseNotificationService;

  @PostMapping(value = ConstAPI.NotificationAPI.REGISTER_DEVICE)
  ApiResponse<Boolean> registerDevice(@RequestBody DeviceRegistrationRequest request) {
    return ApiResponse.<Boolean>builder()
            .result(firebaseNotificationService.registerDeviceToken(request))
            .message("Device registered successfully")
            .build();
  }

  @PostMapping(value = ConstAPI.NotificationAPI.UNREGISTER_DEVICE)
  ApiResponse<Boolean> unregisterDevice(@RequestParam String userId, @RequestParam String token) {
    return ApiResponse.<Boolean>builder()
            .result(firebaseNotificationService.unregisterDeviceToken(userId, token))
            .message("Device unregistered successfully")
            .build();
  }

  @PostMapping(value = ConstAPI.NotificationAPI.SUBSCRIBE_TO_TOPIC)
  ApiResponse<Boolean> subscribeToTopic(@RequestParam String token, @RequestParam String topic) {
    return ApiResponse.<Boolean>builder()
            .result(firebaseNotificationService.subscribeToTopic(token, topic))
            .message("Subscribed to topic successfully")
            .build();
  }

  @PostMapping(value = ConstAPI.NotificationAPI.UNSUBSCRIBE_FROM_TOPIC)
  ApiResponse<Boolean> unsubscribeFromTopic(
          @RequestParam String token, @RequestParam String topic) {
    return ApiResponse.<Boolean>builder()
            .result(firebaseNotificationService.unsubscribeFromTopic(token, topic))
            .message("Unsubscribed from topic successfully")
            .build();
  }

  @PostMapping(value = ConstAPI.NotificationAPI.SEND_TO_TOPIC)
  ApiResponse<String> sendToTopic(
          @RequestParam String topic,
          @RequestParam String title,
          @RequestParam String body,
          @RequestParam(required = false) String data) {
    return ApiResponse.<String>builder()
            .result(firebaseNotificationService.sendNotificationToTopic(topic, title, body, data))
            .message("Notification sent successfully")
            .build();
  }

  @PostMapping(value = ConstAPI.NotificationAPI.SEND_TO_TOKEN)
  ApiResponse<String> sendToToken(
          @RequestParam String token,
          @RequestParam String title,
          @RequestParam String body,
          @RequestParam(required = false) String data) {
    return ApiResponse.<String>builder()
            .result(firebaseNotificationService.sendNotificationToToken(token, title, body, data))
            .message("Notification sent successfully")
            .build();
  }

  @PostMapping(value = ConstAPI.NotificationAPI.SEND_TO_USER)
  ApiResponse<List<String>> sendToUser(
          @RequestParam String userId,
          @RequestParam String title,
          @RequestParam String body,
          @RequestParam(required = false) String data) {
    return ApiResponse.<List<String>>builder()
            .result(firebaseNotificationService.sendNotificationToUser(userId, title, body, data))
            .message("Notifications sent to user devices successfully")
            .build();
  }
}