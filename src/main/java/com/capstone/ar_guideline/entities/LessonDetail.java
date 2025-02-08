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
public class LessonDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "lesson_id", nullable = false)
  private Lesson lesson;

  @OneToMany(mappedBy = "lessonDetail", cascade = CascadeType.ALL)
  private List<LessonProcess> lessonProcesses;

  private String title;
  private Integer orderInLesson;
  private String description;
  private Integer duration;
  private String status;
  private String videoUrl;
  private String attachFileUrl;
  private String type;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdDate;

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedDate;
}
