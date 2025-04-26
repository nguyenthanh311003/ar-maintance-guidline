package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "notifications_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String title;

  private String content;

  private String type;

  private String keyValue;

  private String status;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(updatable = false)
  @CreationTimestamp
  private LocalDateTime createdDate;

  @UpdateTimestamp private LocalDateTime updatedDate;
}
