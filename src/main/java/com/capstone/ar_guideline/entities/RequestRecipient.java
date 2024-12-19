package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RequestRecipient {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "modelRequest_id", nullable = false)
  private ModelRequest modelRequest;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  private String status;
}
