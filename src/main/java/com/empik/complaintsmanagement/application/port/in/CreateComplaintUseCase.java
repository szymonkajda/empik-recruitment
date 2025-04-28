package com.empik.complaintsmanagement.application.port.in;

import com.empik.complaintsmanagement.application.datatype.IpAddress;
import java.time.OffsetDateTime;
import org.springframework.transaction.annotation.Transactional;

public interface CreateComplaintUseCase {

  @Transactional
  void create(Command command);

  record Command(
      Long productId,
      String content,
      OffsetDateTime creationDate,
      String creationUser,
      IpAddress clientIp) {}
}
