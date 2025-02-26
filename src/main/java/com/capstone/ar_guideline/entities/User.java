package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails, Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Enrollment> enrollments;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Result> results;

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

  @Column(unique = true, nullable = false)
  private String email;

  private String avatar;
  private String username;
  private String password;
  private String phone;
  private String status;
  private String expirationDate;
  private String deviceId;
  private Boolean isPayAdmin;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdDate;

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedDate;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.getRoleName()));
  }
}
