package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "options")
public class Option {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;

  @ManyToOne
  @JoinColumn(name = "question_id", nullable = false)
  Question question;

  @OneToMany(mappedBy = "option", cascade = CascadeType.ALL)
  List<ResultDetail> resultDetails;

  @Column(name = "`option`")
  String option;

  Boolean isRight;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  LocalDateTime createdDate;

  @Column(nullable = false)
  @UpdateTimestamp
  LocalDateTime updatedDate;
}
