package org.broxton.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserSignUpRequestDto {
  @NotEmpty(message = "Username is required")
  private String username;
  @NotEmpty(message = "Email is required")
  @Email(message = "Invalid email format")
  private String email;
  @NotEmpty(message = "Password is required")
  private String password;
}
