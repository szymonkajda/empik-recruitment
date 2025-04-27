package com.empik.complaintsmanagement.application.datatype;

import java.time.OffsetDateTime;
import org.springframework.lang.Nullable;

public record ComplaintDto(
    @Nullable Long id,
    Long version,
    Long productId,
    String content,
    OffsetDateTime creationDate,
    String creationUser,
    Integer counter) {

  public ComplaintDto(
      Long productId, String content, OffsetDateTime creationDate, String creationUser) {
    this(null, null, productId, content, creationDate, creationUser, 1);
  }
}
