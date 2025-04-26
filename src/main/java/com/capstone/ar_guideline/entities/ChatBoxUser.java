package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import java.util.UUID;

import lombok.*;


@Setter
@Getter
@Entity
@Table(name = "chat_box_users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatBoxUser {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "chat_box_id", nullable = false)
  private ChatBox chatBox;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
