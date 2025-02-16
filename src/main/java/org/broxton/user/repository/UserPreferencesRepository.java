package org.broxton.user.repository;

import org.broxton.user.entity.UserPreferencesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPreferencesRepository extends JpaRepository<UserPreferencesEntity, Long> {
}
