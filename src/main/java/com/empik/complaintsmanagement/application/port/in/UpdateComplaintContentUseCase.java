package com.empik.complaintsmanagement.application.port.in;

import static io.micrometer.common.util.StringUtils.isBlank;

import org.springframework.transaction.annotation.Transactional;

public interface UpdateComplaintContentUseCase {

  @Transactional
  void update(Command command);

  record Command(Long complaintId, String newContent) {
    public Command {
      if (isBlank(newContent)) {
        throw new IllegalArgumentException("Content must not be empty.");
      }
    }
  }
}
