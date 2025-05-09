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
public class InstructionDetail implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "instruction_id", nullable = false)
  private Instruction instruction;

  private String name;
  private Integer orderNumber;
  private String animationName;
  private String description;

  @Column(length = 1000)
  private String meshes;
  private String status;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdDate;

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedDate;
}
