package org.broxton.listing;

import lombok.RequiredArgsConstructor;
import org.broxton.common.pagination.PaginationResponse;
import org.broxton.common.pagination.PaginationResponseCreator;
import org.broxton.listing.dto.ListingDto;
import org.broxton.user.entity.UserEntity;
import org.broxton.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ListingService {
  private final ListingsRepository listingRepository;
  private final UserService userService;
  private final ListingMapper listingMapper;

  public ResponseEntity<ListingDto> createListing(Long userId, ListingDto listingsDto) {
    UserEntity user = userService.findUserById(userId);
    ListingEntity listing = listingMapper.toEntity(listingsDto, user);
    listingRepository.save(listing);
    return ResponseEntity.ok(listingMapper.toDto(listing));
  }

  public ResponseEntity<ListingDto> updateListing(Long userId, ListingDto listingDto) {
    UserEntity user = userService.findUserById(userId);
    ListingEntity listing = listingRepository.findById(listingDto.id())
            .orElseThrow(() -> new RuntimeException("listing not found"));

    ListingEntity updatedListing = listingMapper.toEntity(listingDto, listing, user);
    listingRepository.save(updatedListing);
    return ResponseEntity.ok(listingMapper.toDto(updatedListing));
  }

  public ResponseEntity<PaginationResponse<ListingDto>> getListings(Pageable pageable) {
    Page<ListingEntity> listingEntityPage = listingRepository.findAll(pageable);
    return ResponseEntity.ok(PaginationResponseCreator.makePaginationResponse(listingEntityPage, listingMapper::toDto));
  }

  public ResponseEntity<PaginationResponse<ListingDto>> getListings(Long userId, Pageable pageable) {
    Page<ListingEntity> listingEntityPage = listingRepository.findByOwnerId(userId, pageable);
    return ResponseEntity.ok(PaginationResponseCreator.makePaginationResponse(listingEntityPage, listingMapper::toDto));
  }

  public ResponseEntity<ListingDto> getListingById(Long id) {
    ListingEntity listingEntity = listingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("listing not found"));
    return ResponseEntity.ok(listingMapper.toDto(listingEntity));
  }

  public void deleteListingById(Long id) {
    if (!listingRepository.existsById(id)) {
      throw new RuntimeException("Listing not found");
    }
    listingRepository.deleteById(id);
  }
}
