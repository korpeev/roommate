package org.broxton.user;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.broxton.user.dto.UserDto;
import org.broxton.user.models.CustomUserDetails;
import org.broxton.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Users")
public class UserController {

  private final UserService userService;

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
}
