package org.broxton.listing;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.broxton.common.CommonValidationGroups;
import org.broxton.common.pagination.PaginationResponse;
import org.broxton.listing.dto.ListingDto;
import org.broxton.listing.dto.validation_groups.UpdateListingValidationGroup;
import org.broxton.user.models.CustomUserDetails;
import org.broxton.utils.PublicAccess;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/listings")
@RequiredArgsConstructor
@Validated
public class ListingController {

  private final ListingService listingService;

  @SecurityRequirement(name = "Authorization")
  @PostMapping("/create")
  @Operation(summary = "Create listing")
  public ResponseEntity<ListingDto> create(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @JsonView(CommonValidationGroups.OnCreate.class)
                                            @RequestBody ListingDto listingDto) {;
    return listingService.createListing(userDetails.getUserId(), listingDto);
  }

  @PublicAccess
  @GetMapping
  public ResponseEntity<PaginationResponse<ListingDto>> getListings(
          @RequestParam(name = "page", defaultValue = "0") int page,
          @RequestParam(name = "page_size", defaultValue = "10") int pageSize
  ) {
    Pageable pageable = PageRequest.of(page, pageSize);

    return listingService.getListings(pageable);
  }

  @SecurityRequirement(name = "Authorization")
  @GetMapping("/me")
  public ResponseEntity<PaginationResponse<ListingDto>> getUserListings(
          @AuthenticationPrincipal CustomUserDetails userDetails,
          @RequestParam(name = "page", defaultValue = "0") int page,
          @RequestParam(name = "page_size", defaultValue = "10") int pageSize
  ) {
    Pageable pageable = PageRequest.of(page, pageSize);

    return listingService.getListings(userDetails.getUserId(), pageable);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<PaginationResponse<ListingDto>> getUserListings(
          @PathVariable Long userId,
          @RequestParam(name = "page", defaultValue = "0") int page,
          @RequestParam(name = "page_size", defaultValue = "10") int pageSize
  ) {
    Pageable pageable = PageRequest.of(page, pageSize);
    return listingService.getListings(userId, pageable);
  }

  @GetMapping("/{listingId}")
  public ResponseEntity<ListingDto> getListingById(@PathVariable Long listingId) {
    return listingService.getListingById(listingId);
  }

  @SecurityRequirement(name = "Authorization")
  @PutMapping("/update")
  @Operation(summary = "Updating listing")
  public ResponseEntity<ListingDto> update(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           @JsonView(CommonValidationGroups.OnUpdate.class)
                                           @Validated(CommonValidationGroups.OnUpdate.class)
                                           @Parameter(hidden = false, description = "ID листинга (обязателен при обновлении)")
                                           @RequestBody
                                           ListingDto listingDto) {
    return listingService.updateListing(userDetails.getUserId(), listingDto);
  }

  @SecurityRequirement(name = "Authorization")
  @DeleteMapping("/{listingId}")
  public ResponseEntity<String> deleteListingById(@PathVariable Long listingId) {
    listingService.deleteListingById(listingId);
    return ResponseEntity.ok("Listing deleted successfully");
  }
}
