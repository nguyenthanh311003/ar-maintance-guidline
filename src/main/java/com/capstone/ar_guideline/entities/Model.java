package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import java.io.Serializable;
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
@Table(name = "asset_model")
public class Model implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;

  @ManyToOne
  @JoinColumn(name = "model_type_id")
  private ModelType modelType;

  private String modelCode;
  private Boolean isUsed;
  private String status;
  private String name;
  private String description;
  private String imageUrl;
  private String version;
  private String scale;
  private String rotation;
  private String position;

  private String file;
  private Double size;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdDate;

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedDate;
}
