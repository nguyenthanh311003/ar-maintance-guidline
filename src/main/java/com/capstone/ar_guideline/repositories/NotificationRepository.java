package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Notification;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
  List<Notification> findAllByUserIdOrderByCreatedDateDesc(String userId);
}
