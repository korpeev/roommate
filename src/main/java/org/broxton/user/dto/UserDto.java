package org.broxton.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
  private String username;
  private String email;
  @JsonProperty("is_banned")
  private Boolean isBanned;
}
