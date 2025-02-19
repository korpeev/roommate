package org.broxton.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateDto {
  private String username;
  private String email;
  private String password;
}
