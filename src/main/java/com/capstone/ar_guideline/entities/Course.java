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
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @OneToOne
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
  private List<Lesson> lessons;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
  private List<Enrollment> enrollments;

  private String title;
  private String description;
  private Integer duration;
  private Boolean isMandatory;
  private String type;
}
