package org.broxton.user.mappers;

import org.broxton.user.dto.UserPreferencesDto;
import org.broxton.user.entity.UserPreferences;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserPreferencesMapper {
  UserPreferencesDto toDto(UserPreferences userPreferences);
  UserPreferences toEntity(UserPreferencesDto userPreferencesDto);
}
