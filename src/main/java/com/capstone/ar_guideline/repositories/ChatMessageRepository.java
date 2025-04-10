package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.ChatMessage;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
  // Find messages for a specific chat box
  List<ChatMessage> findByChatBoxIdOrderByTimestampAsc(UUID chatBoxId);
}
