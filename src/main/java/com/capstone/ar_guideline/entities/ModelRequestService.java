package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ModelRequestService {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToMany(mappedBy = "modelRequestService", cascade = CascadeType.ALL)
    private List<ModelRequest> modelRequests;

    private String type;
    private String modelRequestServiceCode;
    private Double price;
}
