package org.broxton.common;

import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;

public class SnakeToCamelCaseConverter implements Converter<String, String> {
  @Override
  public String convert(String source) {
    if (source.isEmpty()) {
      return source;
    }

    String[] words = source.split("_");
    StringBuilder camelCaseValue = new StringBuilder(words[0]);

    for (int i = 1; i < words.length; i++) {
      camelCaseValue.append(Character.toUpperCase(words[i].charAt(0)))
              .append(words[i].substring(1));
    }

    return camelCaseValue.toString();
  }
}
