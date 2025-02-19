package org.broxton.listing;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.broxton.common.CommonValidationGroups;
import org.broxton.common.pagination.PaginationResponse;
import org.broxton.listing.dto.ListingDto;
import org.broxton.user.models.CustomUserDetails;
import org.broxton.utils.PublicAccess;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/listings")
@RequiredArgsConstructor
@Validated
@Tag(name = "Listings", description = "Listings CRUD")
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
          @RequestParam(required = false, name = "field")
          @Parameter(description = "Select field to sort by", example= "created_at")
          @Schema(allowableValues = {"created_at", "updated_at", "price_per_month"})
          String field,
          @RequestParam(name = "direction", defaultValue = "desc")
          String direction,
          @RequestParam(required = false, name = "searchTerm")
          String search,
          @RequestParam(required = false) Boolean smoking,
          @RequestParam(required = false) Boolean drinking,
          @RequestParam(name = "allow_pets", required = false)
          @Parameter(description = "Filter by allowing pets in listings", example = "true or false")
          Boolean allowPets,
          @RequestParam(name = "min_budget", required = false, defaultValue = "0.00") BigDecimal minBudget,
          @RequestParam(name = "max_budget", required = false, defaultValue = "999999999.00") BigDecimal maxBudget,
          @RequestParam(name = "gender_preference", required = false) String genderPreference,
          @RequestParam(name = "search_term", required = false) String searchTerm,
          @RequestParam(name = "page", defaultValue = "0") int page,
          @RequestParam(name = "page_size", defaultValue = "10") int pageSize
  ) {

    Sort sort = Sort.by(Objects.equals(direction, "asc") ? Sort.Direction.ASC : Sort.Direction.DESC, field);
    Pageable pageable = PageRequest.of(page, pageSize, sort);

    Specification<ListingEntity> spec = Specification
            .where(ListingSpecification.filterByField("smoking", smoking))
            .and(ListingSpecification.filterByField("drinking", drinking))
            .and(ListingSpecification.filterByField("allowPets", allowPets))
            .and(ListingSpecification.filterByField("genderPreference", genderPreference))
            .and(ListingSpecification.filterByPriceRange(minBudget, maxBudget))
            .and(ListingSpecification.searchByTerm(search))
            .and(ListingSpecification.searchByTerm(searchTerm));

    return listingService.getListings(pageable, spec);
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

  @SecurityRequirement(name = "Authorization")
  @GetMapping("/related")
  public ResponseEntity<PaginationResponse<ListingDto>> getUserRelatedListings(
          @AuthenticationPrincipal CustomUserDetails userDetails,
          @RequestParam(name = "page", defaultValue = "0") int page,
          @RequestParam(name = "page_size", defaultValue = "10") int pageSize
  ) {
    Pageable pageable = PageRequest.of(page, pageSize);
    return listingService.getUserRelatedListings(userDetails.getUserId(), pageable);
  }

}
