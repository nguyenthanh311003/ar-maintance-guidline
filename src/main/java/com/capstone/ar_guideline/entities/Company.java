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
public class Company implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
  private List<User> users;

  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
  private List<Machine> machines;

  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
  private List<Course> courses;

  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
  private List<ModelType> modelTypes;

  @Column(unique = true, nullable = false)
  private String companyName;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdDate;

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedDate;
}
