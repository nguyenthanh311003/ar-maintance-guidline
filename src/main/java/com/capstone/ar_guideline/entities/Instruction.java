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
public class Instruction implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "model_id", nullable = false)
  private Model model;

  @OneToMany(mappedBy = "instruction", cascade = CascadeType.ALL)
  private List<InstructionDetail> instructionDetails;

  private String code;
  private Integer orderNumber;
  private String name;
  private String imageUrl;
  private String description;
  private String rotation;
  private String position;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdDate;

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedDate;
}
