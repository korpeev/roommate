package org.broxton.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.broxton.common.BaseEntity;
import org.broxton.profile.GenderType;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Table(name = "user_preferences")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreferencesEntity extends BaseEntity {
  private Boolean smoking;
  private Boolean drinking;

  @Column(name = "allow_pets")
  private Boolean allowPets;

  @Column(name = "min_price")
  private BigDecimal minPrice;
  @Column(name = "max_price")
  private BigDecimal maxPrice;
  @Column(name = "gender_preferences")
  private Set<GenderType> genderPreferences = new HashSet<>();


  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;
}
