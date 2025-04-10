package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // Find messages for a specific chat box
    List<ChatMessage> findByChatBoxIdOrderByTimestampAsc(UUID chatBoxId);
}
