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
public class ModelType {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @OneToMany(mappedBy = "modelType", cascade = CascadeType.ALL)
  private List<Model> models;

  private String name;
  private String image;
  private String description;
}
