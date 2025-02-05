package org.broxton.profile.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.broxton.profile.GenderType;

@Builder
@Getter
@Setter
public class ProfileDto{
  @JsonProperty("fullname")
  private String fullName;
  private int age;
  private String bio;
  private GenderType gender;
  @JsonProperty("profile_photo")
  private String profilePhoto;
}
