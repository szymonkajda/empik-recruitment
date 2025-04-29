package com.empik.complaintsmanagement.application.datatype;

import java.time.OffsetDateTime;

public final class ComplaintTestDataFactory {

  public static final Long COMPLAINT_ID = 10L;
  public static final Long COMPLAINT_VERSION = 5L;
  public static final Long PRODUCT_ID = 1L;
  public static final String CONTENT = "Complaint content";
  public static final OffsetDateTime CREATION_DATE = OffsetDateTime.now();
  public static final String CREATION_USER = "user.one";
  public static final Integer COUNTER = 15;
  public static final String COUNTRY_GERMANY = "Germany";

  private ComplaintTestDataFactory() {}

  public static ComplaintDto createExistingComplaint() {
    return new ComplaintDto(
        COMPLAINT_ID,
        COMPLAINT_VERSION,
        PRODUCT_ID,
        CONTENT,
        CREATION_DATE,
        CREATION_USER,
        COUNTER,
        COUNTRY_GERMANY);
  }
}
