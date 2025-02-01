package org.broxton.user.dto;

import lombok.Data;

@Data
public class UserSignUpRequestDto {
  private String username;
  private String email;
  private String password;
}
