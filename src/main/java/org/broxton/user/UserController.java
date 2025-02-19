package org.broxton.user;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.broxton.common.CommonValidationGroups;
import org.broxton.user.dto.UserDto;
import org.broxton.user.dto.UserPreferencesDto;
import org.broxton.user.models.CustomUserDetails;
import org.broxton.user.service.UserPreferencesService;
import org.broxton.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Users")
@Validated
public class UserController {

  private final UserService userService;
  private final UserPreferencesService userPreferencesService;

  @GetMapping("/me")
  @ApiResponse(
          responseCode = "200",
          description = "get user details",
          useReturnTypeSchema = true,
          content = {
                  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                          schema = @Schema(implementation = UserDto.class))
          }
  )
  public ResponseEntity<UserDto> createUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
    return userService.getMe(userDetails.getUsername());
  }


  @PostMapping("/preferences/create")
  public ResponseEntity<UserPreferencesDto> createUserPreferences(@JsonView(CommonValidationGroups.OnCreate.class)
                                    @Validated(CommonValidationGroups.OnCreate.class)
                                    @RequestBody UserPreferencesDto userPreferencesDto,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
    return userPreferencesService.createUserPreferences(userPreferencesDto, userDetails.getUserId());
  }

  @GetMapping("/preferences")
  public ResponseEntity<UserPreferencesDto> getUserPreferences(@AuthenticationPrincipal CustomUserDetails userDetails) {
    return userPreferencesService.getUserPreferences(userDetails.getUserId());
  }

  @PutMapping("/preferences/update")
  public ResponseEntity<UserPreferencesDto> updateUserPreferences(
          @JsonView(CommonValidationGroups.OnUpdate.class)
          @Validated(CommonValidationGroups.OnUpdate.class)
          @RequestBody UserPreferencesDto userPreferencesDto
  ) {
    return userPreferencesService.updateUserPreferences(userPreferencesDto);
  }

}
