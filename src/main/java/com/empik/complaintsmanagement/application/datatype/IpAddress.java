package com.empik.complaintsmanagement.application.datatype;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

import lombok.NonNull;
import org.springframework.lang.Nullable;

public record IpAddress(@Nullable String value) {
  private static final String UNKNOWN = "unknown";

  public boolean isValid() {
    return isNotBlank(value) && !UNKNOWN.equals(value);
  }

  public @NonNull String getValidatedValue() {
    if (!isValid()) {
      throw new IllegalStateException("Value is invalid.");
    }
    return value;
  }
}
