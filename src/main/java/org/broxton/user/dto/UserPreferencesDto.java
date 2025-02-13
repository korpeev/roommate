package org.broxton.user.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.broxton.common.CommonValidationGroups;
import org.broxton.profile.GenderType;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserPreferencesDto {
  @JsonView(CommonValidationGroups.OnUpdate.class)
  @Null(groups = CommonValidationGroups.OnCreate.class)
  @NotNull(groups = CommonValidationGroups.OnUpdate.class)
  private Long id;
  private Boolean smoking;
  private Boolean drinking;
  private Boolean allowPets;
  private BigDecimal minPrice;
  private BigDecimal maxPrice;
  private Set<GenderType> genderPreferences = new HashSet<>();
}
