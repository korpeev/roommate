package org.broxton.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserSiginRequestDto {

  @NotEmpty(message = "Email is required")
  @Email(message = "Invalid email format")
  @Schema( example = "admin@mail.com", description = "User email")
  private String email;

  @NotEmpty(message = "Password is required")
  @Schema( example = "admin", description = "User password")
  private String password;
}
