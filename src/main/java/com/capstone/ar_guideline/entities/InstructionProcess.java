package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstructionProcess {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "instruction_id", nullable = false)
  private Instruction instruction;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  private Boolean isDone;
}
