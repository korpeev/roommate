package org.broxton.profile;

import jakarta.persistence.*;
import lombok.*;
import org.broxton.user.entity.UserEntity;

@Table(name = "profiles")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProfileEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "profile_photo")
  private String profilePhoto;
  @Column(name = "fullname")
  private String fullName;
  private int age;
  @Enumerated(EnumType.STRING)
  private GenderType gender;
  private String bio;

  @OneToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;
}
