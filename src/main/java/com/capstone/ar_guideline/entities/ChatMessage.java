package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "chat_messages")
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, length = 1000)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private LocalDateTime timestamp;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_box_id", nullable = false)
  private ChatBox chatBox;

  @PrePersist
  protected void onCreate() {
    this.timestamp = LocalDateTime.now();
  }
}
