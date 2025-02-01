package org.broxton.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record RefreshTokenRequestDto(
        @NotNull(message = "refreshToken is required")
        @NotBlank(message = "refreshToken not a blank")
        String refreshToken
) {
}
