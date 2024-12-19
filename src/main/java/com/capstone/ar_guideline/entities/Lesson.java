package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Lesson {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
  private List<ModelLesson> modelLessons;

  @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
  private List<LessonProcess> lessonProcesses;

  private String title;
  private Integer orderInCourse;
  private String description;
  private Integer duration;
}
