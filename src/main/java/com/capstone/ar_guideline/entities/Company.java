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
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<User> users;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<CompanySubscription> companySubscriptions;

    private String companyName;
}
