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
@Table(name = "Machine_Type")
public class ModelType implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @OneToMany(mappedBy = "modelType", cascade = CascadeType.ALL)
  private List<Machine> machines;

  @OneToMany(mappedBy = "modelType", cascade = CascadeType.ALL)
  private List<MachineTypeAttribute> machineTypeAttributes;

  @OneToMany(mappedBy = "modelType", cascade = CascadeType.ALL)
  private List<Model> machineTypeValues;

  @OneToMany(mappedBy = "modelType", cascade = CascadeType.ALL)
  private List<Course> courses;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;

  private String name;
  private String image;
  private String description;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdDate;

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedDate;
}
