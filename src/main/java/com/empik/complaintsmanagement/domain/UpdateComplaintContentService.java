package com.empik.complaintsmanagement.domain;

import com.empik.complaintsmanagement.application.datatype.ComplaintDto;
import com.empik.complaintsmanagement.application.port.in.UpdateComplaintContentUseCase;
import com.empik.complaintsmanagement.application.port.out.FindComplaintPort;
import com.empik.complaintsmanagement.application.port.out.PersistComplaintPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class UpdateComplaintContentService implements UpdateComplaintContentUseCase {

  private final FindComplaintPort findComplaintPort;
  private final PersistComplaintPort persistComplaintPort;

  @Override
  @Transactional
  public void update(Command command) {
    ComplaintDto complaintDto =
        findComplaintPort
            .findBy(command.complaintId())
            .orElseThrow(() -> new EntityNotFoundException("Complaint does not exist."));
    Complaint complaint = Complaint.from(complaintDto);

    complaint.setContent(command.newContent());
    persistComplaintPort.persist(complaint.toDto());
  }
}
