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
public class Instruction {
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
  private String description;
}
