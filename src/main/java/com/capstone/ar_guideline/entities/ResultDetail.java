package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ResultDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;

  @ManyToOne
  @JoinColumn(name = "question_id", nullable = false)
  Question question;

  @ManyToOne
  @JoinColumn(name = "result_id", nullable = false)
  Result result;

  @ManyToOne
  @JoinColumn(name = "option_id", nullable = false)
  Option option;

  Boolean isRight;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  LocalDateTime createdDate;

  @Column(nullable = false)
  @UpdateTimestamp
  LocalDateTime updatedDate;
}
