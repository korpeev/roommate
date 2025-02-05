package org.broxton.profile.mapper;

import org.broxton.profile.ProfileEntity;
import org.broxton.profile.dto.ProfileCreateRequestDto;
import org.broxton.profile.dto.ProfileDto;
import org.broxton.profile.dto.ProfileUpdateRequestDto;
import org.broxton.user.entity.UserEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfileMapper {
  @Mapping(target = "user", expression = "java(user)")
  ProfileEntity toEntity(ProfileDto dto, @Context UserEntity user);

  @Mapping(target = "user", expression = "java(user)")
  ProfileEntity toEntity(ProfileCreateRequestDto dto, @Context UserEntity user);

  @Mapping(target = "id", ignore = true)
  ProfileEntity toEntity(ProfileUpdateRequestDto dto, @MappingTarget ProfileEntity existingProfile);

  ProfileDto toDto(ProfileEntity entity);
}
