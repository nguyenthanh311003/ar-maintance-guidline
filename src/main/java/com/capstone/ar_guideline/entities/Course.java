package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import java.io.Serializable;
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
public class Course implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @ManyToOne
  @JoinColumn(name = "model_id", unique = true)
  private Model model;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
  private List<Instruction> instructions;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
  private List<Lesson> lessons;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
  private List<Enrollment> enrollments;

  private String title;
  private String description;
  private String shortDescription;
  private Integer duration;
  private Boolean isMandatory;
  private String targetAudience;
  private String type;
  private String status;
  private String imageUrl;
  private String qrCode;
  private String courseCode;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdDate;

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedDate;
}
