package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InstructionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "instruction_id", nullable = false)
    private Instruction instruction;

    private String triggerEvent;
    private Integer order;
    private String description;
}
