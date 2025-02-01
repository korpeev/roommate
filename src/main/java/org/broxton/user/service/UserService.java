package org.broxton.user.service;

import lombok.RequiredArgsConstructor;
import org.broxton.exceptions.*;
import org.broxton.user.dto.*;
import org.broxton.user.entity.Role;
import org.broxton.user.entity.UserEntity;
import org.broxton.user.models.UserRoles;
import org.broxton.user.repository.RoleRepository;
import org.broxton.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;


  public UserEntity createUser(UserSignUpRequestDto dto) {
    userRepository.findByEmail(dto.getEmail()).ifPresent(existingUser -> {
      throw new UserAlreadyExistsException("User already exists");
    });

    UserEntity user = new UserEntity();
    user.setUsername(dto.getUsername());
    user.setEmail(dto.getEmail());
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
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

    return new ResponseEntity<>(UserDto.builder()
            .isBanned(user.getIsBanned())
            .email(user.getEmail())
            .username(user.getUsername())
            .build(), HttpStatus.OK);
  };

  public UserEntity findUserById(Long id) {
    return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found by given email"));
  }

  public void updateUserById(Long id, UserUpdateDto dto) {
    UserEntity user = findUserById(id);
    user.setUsername(dto.getUsername());
    user.setEmail(dto.getEmail());
    user.setIsBanned(dto.getIsBanned());
    user.setRefreshToken(dto.getRefreshToken());

    userRepository.save(user);
  }

  public void updateUserRefreshToken(Long id, String refreshToken) {
    UserEntity user = findUserById(id);
    user.setRefreshToken(refreshToken);
    userRepository.save(user);

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
