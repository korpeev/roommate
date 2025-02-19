package org.broxton.profile;

import lombok.RequiredArgsConstructor;
import org.broxton.exceptions.ProfileAlreadyExist;
import org.broxton.exceptions.ProfileNotFoundException;
import org.broxton.profile.dto.ProfileCreateRequestDto;
import org.broxton.profile.dto.ProfileDto;
import org.broxton.profile.dto.ProfileUpdateRequestDto;
import org.broxton.profile.mapper.ProfileMapper;
import org.broxton.user.entity.UserEntity;
import org.broxton.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ProfileService {
  private final ProfileRepository profileRepository;
  private final UserService userService;
  private final ProfileMapper profileMapper;

  private final String UPLOAD_DIR = "/opt/uploads/";

  public ResponseEntity<ProfileDto> createProfile(Long userId, ProfileCreateRequestDto dto) {
    profileRepository.findProfileByUserId(userId).ifPresent(existProfile -> {
      throw new ProfileAlreadyExist("Profile already exist");
    });

    UserEntity user = userService.findUserById(userId);

    ProfileEntity profileEntity = profileMapper.toEntity(dto, user);
    profileRepository.save(profileEntity);

    return new ResponseEntity<>(profileMapper.toDto(profileEntity), HttpStatus.CREATED);

  }

  public ResponseEntity<ProfileDto> updateProfile(Long userId, ProfileUpdateRequestDto dto) {
    ProfileEntity profileEntity = profileRepository.findProfileByUserId(userId)
            .orElseThrow(() -> new ProfileNotFoundException("User profile not found by user id"));

    ProfileEntity updatedProfileEntity = profileMapper.toEntity(dto, profileEntity);
    profileRepository.save(updatedProfileEntity);

    return new ResponseEntity<>(profileMapper.toDto(updatedProfileEntity), HttpStatus.OK);
  }

  public ResponseEntity<String> updateProfilePhoto(Long userId, MultipartFile profilePhoto) {

    if (profilePhoto.isEmpty()) {
      return ResponseEntity.badRequest().body("No file uploaded");
    }

    try {
      Path uploadPath = Paths.get(UPLOAD_DIR);

      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
      }


      String fileName = "%s_%s".formatted(userId, profilePhoto.getOriginalFilename());
      Path filePath = uploadPath.resolve(fileName);
      profilePhoto.transferTo(filePath);


      ProfileEntity profileEntity = profileRepository.findProfileByUserId(userId)
              .orElseThrow(() -> new ProfileNotFoundException("User profile not found by user id"));

      profileEntity.setProfilePhoto("/uploads/" + fileName);

      profileRepository.save(profileEntity);

      return ResponseEntity.ok("File uploaded successfully");
    } catch (IOException e) {
      return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
    }

  }

  public ResponseEntity<String> deleteProfilePhoto(Long userId) {
    try {

      ProfileEntity profileEntity = profileRepository.findProfileByUserId(userId)
              .orElseThrow(() -> new ProfileNotFoundException("User profile not found by user id"));

      Path filePath = Paths.get(profileEntity.getProfilePhoto());
      Files.delete(filePath);

      return ResponseEntity.ok().body("Profile photo deleted successfully");
    } catch (IOException e) {
      return ResponseEntity.status(500).body("File deleting failed: " + e.getMessage());
    }
  }
}
