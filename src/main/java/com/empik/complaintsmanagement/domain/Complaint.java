package com.empik.complaintsmanagement.domain;

import com.empik.complaintsmanagement.application.datatype.ComplaintDto;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
class Complaint {
  @Nullable private final Long id;
  private final Long version;
  private Long productId;
  private String content;
  private OffsetDateTime creationDate;
  private String creationUser;
  private Integer counter;
  @Nullable private String countryName;

  public static Complaint from(ComplaintDto complaint) {
    return new Complaint(
        complaint.id(),
        complaint.version(),
        complaint.productId(),
        complaint.content(),
        complaint.creationDate(),
        complaint.creationUser(),
        complaint.counter(),
        complaint.countryName());
  }

  public void incrementCounter() {
    counter++;
  }

  public ComplaintDto toDto() {
    return new ComplaintDto(
        id, version, productId, content, creationDate, creationUser, counter, countryName);
  }
}
