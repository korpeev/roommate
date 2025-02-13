package org.broxton.listing;

import org.broxton.listing.dto.ListingDto;
import org.broxton.user.entity.UserEntity;
import org.broxton.user.mappers.UserMapper;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface ListingMapper {

  @Mapping(target = "owner", expression = "java(user)")
  ListingEntity toEntity(ListingDto dto, @Context UserEntity user);

  @Mapping(target = "owner", expression = "java(user)")
  @Mapping(target = "id", ignore = true)
  ListingEntity toEntity(ListingDto dto, @MappingTarget ListingEntity existingListing, @Context UserEntity user);

  ListingDto toDto(ListingEntity entity);
}
