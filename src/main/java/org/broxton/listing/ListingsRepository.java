package org.broxton.listing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingsRepository extends JpaRepository<ListingEntity, Long> {

  Page<ListingEntity> findAll(Pageable pageable);
  Page<ListingEntity> findByOwnerId(Long ownerId, Pageable pageable);
}
