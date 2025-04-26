package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findAllByUserId(String userId);
}
