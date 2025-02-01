package org.broxton.utils;

import lombok.Getter;

@Getter
public enum TokenType {
  ACCESS("access"),
  REFRESH("refresh");

  private final String name;

  TokenType(String name) {
    this.name = name;
  }
}
