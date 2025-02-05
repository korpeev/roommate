package org.broxton.config;

import com.fasterxml.jackson.annotation.JsonInclude.Value;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class JacksonConfig {

  @Bean
  public ObjectMapper objectMapper() {

    ObjectMapper objectMapper = new ObjectMapper();

    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    objectMapper.configOverride(Map.class)
            .setInclude(Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.NON_NULL));

    return objectMapper;

  }
}
