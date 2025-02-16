package org.broxton.user.service;

import lombok.RequiredArgsConstructor;
import org.broxton.user.dto.UserPreferencesDto;
import org.broxton.user.entity.UserEntity;
import org.broxton.user.entity.UserPreferencesEntity;
import org.broxton.user.mappers.UserPreferencesMapper;
import org.broxton.user.repository.UserPreferencesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPreferencesService {
  private final UserPreferencesRepository userPreferencesRepository;
  private final UserService userService;
  private final UserPreferencesMapper userPreferencesMapper;

  public ResponseEntity<UserPreferencesDto> createUserPreferences(UserPreferencesDto dto, Long userId) {
    UserEntity user = userService.findUserById(userId);

    if (user.getUserPreferences() != null) {
      throw new RuntimeException("User preferences already exist");
    }

    UserPreferencesEntity userPreferences = userPreferencesMapper.toEntity(dto, user);
    userPreferencesRepository.save(userPreferences);
    return new ResponseEntity<>(userPreferencesMapper.toDto(userPreferences), HttpStatus.CREATED);
  }

  public ResponseEntity<UserPreferencesDto> updateUserPreferences(UserPreferencesDto dto) {
    UserPreferencesEntity userPreferences = userPreferencesRepository.findById(dto.getId())
            .orElseThrow(() -> new RuntimeException("User preferences not found"));

    UserPreferencesEntity updatedUserPreferences = userPreferencesMapper.updateEntityFromDto(dto, userPreferences);
    userPreferencesRepository.save(updatedUserPreferences);

    return new ResponseEntity<>(userPreferencesMapper.toDto(updatedUserPreferences), HttpStatus.OK);
  }

  public ResponseEntity<UserPreferencesDto> getUserPreferences(Long userId) {
    UserEntity user = userService.findUserById(userId);
    if (user.getUserPreferences() == null) {
      throw new RuntimeException("User preferences not found");
    }
    return new ResponseEntity<>(userPreferencesMapper.toDto(user.getUserPreferences()), HttpStatus.OK);

  }
}
