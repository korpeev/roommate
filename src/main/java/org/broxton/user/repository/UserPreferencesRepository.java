package org.broxton.user.repository;

import org.broxton.user.entity.UserPreferencesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPreferencesRepository extends JpaRepository<UserPreferencesEntity, Long> {
  Optional<UserPreferencesEntity> findUserPreferencesByUserId(Long userId);
}
