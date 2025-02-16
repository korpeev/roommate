package org.broxton.user.mappers;

import org.broxton.user.dto.UserPreferencesDto;
import org.broxton.user.entity.UserEntity;
import org.broxton.user.entity.UserPreferencesEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserPreferencesMapper {
  UserPreferencesDto toDto(UserPreferencesEntity userPreferences);

  @Mapping(target = "id", ignore = true)
  UserPreferencesEntity updateEntityFromDto(UserPreferencesDto dto, @MappingTarget UserPreferencesEntity entity);


  @Mapping(target = "user", expression = "java(user)")
  UserPreferencesEntity toEntity(UserPreferencesDto userPreferencesDto, @Context UserEntity user);
}
