package org.broxton.user.repository;

import org.broxton.user.dto.UserPreferencesDto;
import org.broxton.user.entity.UserEntity;
import org.broxton.user.entity.UserPreferencesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByEmail(String email);

  @Modifying
  @Query("UPDATE UserEntity u SET u.refreshToken = :refreshToken WHERE u.id = :id")
  void updateRefreshToken(@Param("id") Long id, @Param("refreshToken") String refreshToken);


  @Query("SELECT u.userPreferences FROM UserEntity u WHERE u.id = :userId")
  Optional<UserPreferencesEntity> findUserPreferencesByUserId(@Param("userId") Long userId);

  @Modifying
  @Query("""
          UPDATE UserEntity u SET u.userPreferences = :userPreferences WHERE u.id = :userId
          """)
  void updateUserPreferencesByUserId(@Param("userId") Long userId, @Param("userPreferences") UserPreferencesEntity entity);
}
