package org.broxton.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.broxton.common.BaseEntity;
import org.broxton.profile.GenderType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "user_preferences")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreferencesEntity {

  @Id
  private Long id;

  private Boolean smoking;
  private Boolean drinking;

  @Column(name = "allow_pets")
  private Boolean allowPets;

  @Column(name = "min_budget")
  private BigDecimal minPrice;
  @Column(name = "max_budget")
  private BigDecimal maxPrice;
  @Column(name = "gender_preference")
  @Enumerated(EnumType.STRING)
  private GenderType genderPreference;


  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  @MapsId
  private UserEntity user;

}
