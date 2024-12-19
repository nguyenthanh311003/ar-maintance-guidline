package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "modelType_id", nullable = false)
    private ModelType modelType;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL)
    private List<Instruction> instructions;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL)
    private List<ModelRequest> modelRequests;

    private String modelCode;
    private String status;
    private String name;
    private String description;
    private String image;
    private String documentUrl;
    private String ARUrl;
    private String version;
    private String rotation;
    private String scale;
    private String fileType;
    private Long fileSize;
}
