package org.broxton.listing;

import org.broxton.profile.GenderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ListingsRepository extends JpaRepository<ListingEntity, Long>, JpaSpecificationExecutor<ListingEntity> {


  Page<ListingEntity> findByOwnerId(Long ownerId, Pageable pageable);

  @Query("""
    SELECT l FROM ListingEntity l
    ORDER BY 
        CASE WHEN l.pricePerMonth BETWEEN :minBudget AND :maxBudget THEN 30 ELSE 0 END +
        CASE WHEN l.smoking = :smoking THEN 20 ELSE 0 END +
        CASE WHEN l.drinking = :drinking THEN 20 ELSE 0 END +
        CASE WHEN l.allowPets = :allowPets THEN 10 ELSE 0 END +
        CASE WHEN l.genderPreference = :genderPreference THEN 15 ELSE 0 END DESC
""")
  Page<ListingEntity> findMatchingListings(
          @Param("smoking") Boolean smoking,
          @Param("drinking") Boolean drinking,
          @Param("allowPets") Boolean allowPets,
          @Param("minBudget") BigDecimal minBudget,
          @Param("maxBudget") BigDecimal maxBudget,
          @Param("genderPreference") GenderType genderPreference,
          Pageable pageable
  );


}
