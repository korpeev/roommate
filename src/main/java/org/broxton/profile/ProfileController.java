package org.broxton.profile;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.broxton.profile.dto.ProfileCreateRequestDto;
import org.broxton.profile.dto.ProfileDto;
import org.broxton.profile.dto.ProfileUpdateRequestDto;
import org.broxton.user.models.CustomUserDetails;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Profile")
@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ProfileController {

  private final ProfileService profileService;


  @PostMapping("/create")
  public ResponseEntity<ProfileDto> createProfile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @RequestBody ProfileCreateRequestDto dto) {
    return profileService.createProfile(userDetails.getUserId(), dto);
  }

  @PutMapping("/update")
  public ResponseEntity<ProfileDto> updateProfile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @RequestBody ProfileUpdateRequestDto dto) {
    return profileService.updateProfile(userDetails.getUserId(), dto);
  }

  @PatchMapping(value = "/update-profile-photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> updateProfilePhoto(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                   @RequestParam("file") MultipartFile file) {
    return profileService.updateProfilePhoto(userDetails.getUserId(), file);
  }

  @DeleteMapping("/delete-profile-photo")
  public ResponseEntity<String> deleteProfilePhoto(@AuthenticationPrincipal CustomUserDetails userDetails) {
    return profileService.deleteProfilePhoto(userDetails.getUserId());
  }
}
