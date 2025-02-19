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
  private Long id;

  @Column(name = "profile_photo")
  private String profilePhoto;
  @Column(name = "full_name")
  private String fullName;
  private int age;
  @Enumerated(EnumType.STRING)
  private GenderType gender;
  private String bio;

  @OneToOne
  @JoinColumn(name = "user_id")
  @MapsId
  private UserEntity user;
}
