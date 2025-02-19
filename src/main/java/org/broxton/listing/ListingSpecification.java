package org.broxton.listing;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ListingSpecification {

  public static Specification<ListingEntity> filterByField(String fieldName, Object value) {
    return (root, query, criteriaBuilder) ->
            value != null ? criteriaBuilder.equal(root.get(fieldName), value) : null;
  }

  public static Specification<ListingEntity> filterByPriceRange(BigDecimal minBudget, BigDecimal maxBudget) {
    return (root, query, criteriaBuilder) -> {
      if (minBudget != null && maxBudget != null) {
        return criteriaBuilder.between(root.get("pricePerMonth"), minBudget, maxBudget);
      }
      return null;
    };
  }

  public static Specification<ListingEntity> searchByTerm(String searchTerm) {
    return (root, query, criteriaBuilder) -> {
      if (searchTerm != null && !searchTerm.isEmpty()) {
        String pattern = "%" + searchTerm.toLowerCase() + "%";
        Predicate titleMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), pattern);
        Predicate descriptionMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), pattern);
        return criteriaBuilder.or(titleMatch, descriptionMatch);
      }
      return null;
    };
  }
}
