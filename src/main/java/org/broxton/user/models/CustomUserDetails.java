package org.broxton.user.models;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

  @Getter
  private final Long userId;
  private final String email;
  private final String password;
  private final Boolean isBanned;
  private final Collection<? extends GrantedAuthority> authorities;

  public CustomUserDetails(Long userId, String email, String password, Boolean isBanned, List<GrantedAuthority> authorities) {
    this.userId = userId;
    this.email = email;
    this.password = password;
    this.isBanned = isBanned;
    this.authorities = authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return isBanned;
  }
}
