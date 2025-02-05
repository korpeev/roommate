package org.broxton.user.mappers;

import org.broxton.user.dto.UserAuthDto;
import org.broxton.user.dto.UserDto;
import org.broxton.user.dto.UserSignUpRequestDto;
import org.broxton.user.dto.UserUpdateDto;
import org.broxton.user.entity.UserEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserDto toDto(UserEntity user);

  UserEntity toEntity(UserDto userDto);


  @Mappings(value = {
          @Mapping(target = "isBanned", constant = "false"),
          @Mapping(target = "profile", expression = "java(null)"),
          @Mapping(target = "password", expression = "java(passwordEncoder.encode(userSignUpRequestDto.getPassword()))")
  })
  UserEntity toEntity(UserSignUpRequestDto userSignUpRequestDto, @Context PasswordEncoder passwordEncoder);

  @Mapping(target = "id", ignore = true)
  void toEntityUserUpdateDto(UserUpdateDto dto, @MappingTarget UserEntity existingUser);

}
