package org.broxton.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.broxton.exceptions.*;
import org.broxton.user.dto.*;
import org.broxton.user.entity.Role;
import org.broxton.user.entity.UserEntity;
import org.broxton.user.entity.UserPreferencesEntity;
import org.broxton.user.mappers.UserMapper;
import org.broxton.user.mappers.UserPreferencesMapper;
import org.broxton.user.models.UserRoles;
import org.broxton.user.repository.RoleRepository;
import org.broxton.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;


  @Transactional
  public UserEntity createUser(UserSignUpRequestDto dto) {
    userRepository.findByEmail(dto.getEmail()).ifPresent(existingUser -> {
      throw new UserAlreadyExistsException("User already exists");
    });

    UserEntity user = userMapper.toEntity(dto, passwordEncoder);
    setDefaultUserRoleForNewUser(user);

    userRepository.save(user);
    return findUserByEmail(user.getEmail());
  }

  public UserEntity validateAndGetUser(UserSiginRequestDto dto) {

    UserEntity user = findUserByEmail(dto.getEmail());

    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
      throw new InvalidCredentialsException("Invalid credentials");
    }

    if (user.getIsBanned()) {
      throw new UserNotAccessException("User banned in system, please contact admins");
    }

    return user;
  }

  public ResponseEntity<UserDto> getMe(String email) {
    UserEntity user = findUserByEmail(email);
    return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
  };

  public UserEntity findUserById(Long id) {
    return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found by given id"));
  }

  public void updateUserById(Long id, UserUpdateDto dto) {
    UserEntity user = findUserById(id);
    userMapper.toEntityUserUpdateDto(dto, user);
    userRepository.save(user);
  }

  @Transactional
  public void updateUserRefreshToken(Long id, String refreshToken) {
    userRepository.updateRefreshToken(id, refreshToken);
  }

  public UserEntity findUserByEmail(String email) {
   return userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User not found by given email"));
  }

  private void setDefaultUserRoleForNewUser(UserEntity user) {
    Role role = roleRepository.findByName(UserRoles.valueOf("USER"))
            .orElseThrow(() -> new RoleNotFoundException("role not found"));

    user.setRoles(Set.of(role));

  }
}
