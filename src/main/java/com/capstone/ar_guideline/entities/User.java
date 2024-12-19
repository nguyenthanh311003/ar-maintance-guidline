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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LessonProcess> lessonProcesses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Log> logs;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<OrderTransaction> orderTransactions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RequestRecipient> requestRecipients;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private String email;
    private String avatar;
    private String username;
    private String password;
    private String phone;
    private String status;
    private String expirationDate;
    private Boolean isPayAdmin;
}
