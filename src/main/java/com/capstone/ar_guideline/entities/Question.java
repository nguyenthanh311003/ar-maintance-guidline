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
public class Question {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;

  @ManyToOne
  @JoinColumn(name = "quiz_id", nullable = false)
  Quiz quiz;

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
  List<Option> options;

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
  List<ResultDetail> resultDetails;

  String question;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  LocalDateTime createdDate;

  @Column(nullable = false)
  @UpdateTimestamp
  LocalDateTime updatedDate;
}
