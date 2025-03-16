package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Machine_Type_Attribute")
public class MachineTypeAttribute implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "modelType_id")
    private ModelType modelType;

    @OneToMany(mappedBy = "machineTypeAttribute", cascade = CascadeType.ALL)
    private List<MachineTypeValue> machineTypeValues;

    String attributeName;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedDate;
}
