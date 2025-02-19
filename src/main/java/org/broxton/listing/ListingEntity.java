package org.broxton.listing;

import jakarta.persistence.*;
import lombok.*;
import org.broxton.common.BaseEntity;
import org.broxton.profile.GenderType;
import org.broxton.user.entity.UserEntity;
import java.math.BigDecimal;

@Table(name = "listings",
        indexes = {
        @Index(name = "idx_title", columnList = "title"),
        @Index(name = "idx_description", columnList = "description"),
        @Index(name = "idx_price_per_month", columnList = "price_per_month"),
        @Index(name = "idx_created_at", columnList = "created_at"),
        @Index(name = "idx_updated_at", columnList = "updated_at"),
})
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListingEntity extends BaseEntity {

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @Column(name = "price_per_month", nullable = false)
  private BigDecimal pricePerMonth;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private UserEntity owner;

  @Enumerated(EnumType.STRING)
  private ListingType type;

  @Column(columnDefinition = "BOOLEAN DEFAULT false")
  private Boolean smoking;
  @Column(columnDefinition = "BOOLEAN DEFAULT false")
  private Boolean drinking;
  @Column(columnDefinition = "BOOLEAN DEFAULT false")
  private Boolean allowPets;

  @Column(name = "gender_preference")
  @Enumerated(EnumType.STRING)
  private GenderType genderPreference;

}
