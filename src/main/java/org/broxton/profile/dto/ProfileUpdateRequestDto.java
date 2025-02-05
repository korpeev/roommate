package org.broxton.profile.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import org.broxton.profile.GenderType;

@Getter
public class ProfileUpdateRequestDto {
  private String fullName;

  @Min(16)
  @Max(60)
  private int age;
  private String bio;

  private GenderType gender;
  private String profilePhoto;
}
