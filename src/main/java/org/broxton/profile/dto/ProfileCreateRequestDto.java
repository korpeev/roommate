package org.broxton.profile.dto;

import lombok.Data;
import org.broxton.profile.GenderType;

@Data
public class ProfileCreateRequestDto {
  private String fullName;
  private int age;
  private String bio;
  private GenderType gender;
  private String profilePhoto;
}
