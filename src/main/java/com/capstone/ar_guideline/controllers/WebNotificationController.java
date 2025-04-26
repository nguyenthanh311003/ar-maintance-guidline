package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.dtos.requests.Notification.NotificationRequest;
import com.capstone.ar_guideline.dtos.responses.Notification.NotificationResponse;
import com.capstone.ar_guideline.services.impl.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/web/notifications")
@RequiredArgsConstructor
public class WebNotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponse> create(@RequestBody NotificationRequest request) {
        return ResponseEntity.ok(notificationService.create(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getAllByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.getAllByUserId(userId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<NotificationResponse> changeStatus(@PathVariable UUID id, @RequestParam String status) {
        return ResponseEntity.ok(notificationService.changeStatus(id, status));
    }
}