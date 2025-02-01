package org.broxton.user.dto;

import lombok.Data;

@Data
public class UserSiginRequestDto {
  private String email;
  private String password;
}
