package org.broxton.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserSiginRequestDto {

  @NotEmpty(message = "Email is required")
  @Email(message = "Invalid email format")
  private String email;

  @NotEmpty(message = "Password is required")
  private String password;
}
