package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "chat_boxes")
@NoArgsConstructor
@AllArgsConstructor
public class ChatBox {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_request_id", nullable = false)
  private CompanyRequest companyRequest;

  @OneToMany(
      mappedBy = "chatBox",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      orphanRemoval = true)
  private List<ChatMessage> messages = new ArrayList<>();

  // Users in the chat box (many-to-many relationship could be added here)
  @OneToMany(
      mappedBy = "chatBox",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      orphanRemoval = true)
  private List<ChatBoxUser> participants = new ArrayList<>();

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

  // Convenience method to add messages
  public void addMessage(ChatMessage message) {
    messages.add(message);
    message.setChatBox(this);
  }
}
