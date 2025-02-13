package org.broxton.listing.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.broxton.common.CommonValidationGroups;
import org.broxton.listing.ListingType;
import org.broxton.user.dto.UserDto;

import java.math.BigDecimal;


public record ListingDto(

        @JsonView(CommonValidationGroups.OnUpdate.class)
        @NotNull(groups = CommonValidationGroups.OnUpdate.class)
        @Null(groups = CommonValidationGroups.OnCreate.class)
        Long id,

        @NotBlank
        String title,

        @NotBlank
        String description,

        @NotNull
        @DecimalMin(value = "0.01", inclusive = true)
        BigDecimal pricePerMonth,

        ListingType type,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        @Null(groups = {CommonValidationGroups.OnCreate.class, CommonValidationGroups.OnUpdate.class})
        UserDto owner
) {
}
