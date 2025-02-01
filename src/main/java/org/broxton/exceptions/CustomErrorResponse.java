package org.broxton.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CustomErrorResponse {
  private String message;
  private LocalDateTime timestamp;
  @JsonProperty("status_code")
  private Integer statusCode;
}
