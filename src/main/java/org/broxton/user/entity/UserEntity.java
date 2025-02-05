package org.broxton.user.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.broxton.profile.ProfileEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_email", columnList = "email"),
                @Index(name = "idx_id", columnList = "id"),
        }
)
@Entity
@Getter
@Setter
@ToString
public class UserEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;
  @Column(unique = true, nullable = false)
  private String email;

  private String password;

  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
  @JoinTable(
          name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn( name = "role_id")
  )
  private Set<Role> roles = new HashSet<>();

  @Column(name = "is_banned")
  private Boolean isBanned = false;

  @Column(name = "refresh_token")
  private String refreshToken;

  @OneToOne(mappedBy = "user", cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
  private ProfileEntity profile;


  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
