package org.broxton.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.broxton.auth.dto.TokensGeneratedDto;


@Builder
@Data
@ToString
public class UserAuthDto {
  private UserDto user;
  private TokensGeneratedDto tokens;
}
