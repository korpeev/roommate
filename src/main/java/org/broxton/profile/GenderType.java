package org.broxton.profile;

import lombok.Getter;

@Getter
public enum GenderType {
  MALE("male"),
  FEMALE("female"),
  OTHER("other");

  private final String label;

  GenderType(String label) {
    this.label = label;
  }

}
