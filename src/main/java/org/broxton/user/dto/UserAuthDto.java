package org.broxton.user.dto;

import lombok.Builder;
import lombok.Data;
import org.broxton.auth.dto.TokensGeneratedDto;


@Builder
@Data
public class UserAuthDto {
  private UserDto user;
  private TokensGeneratedDto tokens;
}
