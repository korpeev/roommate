package org.broxton.listing;

import jakarta.persistence.*;
import lombok.*;
import org.broxton.common.BaseEntity;
import org.broxton.user.entity.UserEntity;

import java.math.BigDecimal;

@Table(name = "listings")
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

}
