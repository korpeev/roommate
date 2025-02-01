package org.broxton.user.models;

import lombok.Getter;

@Getter
public enum UserRoles {
  ADMIN("ADMIN"),
  USER("USER");

  private String role;


  UserRoles(String role) {
    this.role = role;
  }
}
