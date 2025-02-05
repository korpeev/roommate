package org.broxton.auth.mapper;

import org.broxton.auth.dto.TokensGeneratedDto;
import org.broxton.user.dto.UserAuthDto;
import org.broxton.user.entity.UserEntity;
import org.broxton.user.mappers.UserMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AuthMapper {
  AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

  @Mapping(target = "tokens", expression = "java(tokens)")
  @Mapping(target = "user", source = "user")
  UserAuthDto toUserAuthDto(UserEntity user, @Context TokensGeneratedDto tokens);

}
