package org.broxton.user.dto;

import lombok.Builder;
import lombok.Data;
import org.broxton.profile.dto.ProfileDto;

import java.time.LocalDateTime;

@Data
@Builder
public class UserDto {
  private String username;
  private String email;
  private Boolean isBanned;
  private ProfileDto profile;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private UserPreferencesDto userPreferences;
}
