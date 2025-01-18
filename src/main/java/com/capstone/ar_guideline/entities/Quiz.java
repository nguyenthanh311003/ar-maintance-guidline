package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Quiz {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;

  @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
  List<Question> questions;

  @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
  List<Result> results;

  String description;
  String title;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  LocalDateTime createdDate;

  @Column(nullable = false)
  @UpdateTimestamp
  LocalDateTime updatedDate;
}
