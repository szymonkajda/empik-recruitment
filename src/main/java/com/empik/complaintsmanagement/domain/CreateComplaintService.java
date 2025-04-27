package com.empik.complaintsmanagement.domain;

import com.empik.complaintsmanagement.application.datatype.ComplaintDto;
import com.empik.complaintsmanagement.application.port.in.CreateComplaintUseCase;
import com.empik.complaintsmanagement.application.port.out.FindComplaintPort;
import com.empik.complaintsmanagement.application.port.out.PersistComplaintPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class CreateComplaintService implements CreateComplaintUseCase {

  private final FindComplaintPort findComplaintPort;
  private final PersistComplaintPort persistComplaintPort;

  @Override
  @Transactional
  public void create(Command command) {
    Optional<ComplaintDto> optionalComplaint =
        findComplaintPort.findBy(command.productId(), command.creationUser());

    if (optionalComplaint.isPresent()) {
      Complaint complaint = Complaint.from(optionalComplaint.get());
      complaint.incrementCounter();
      persistComplaintPort.persist(complaint.toDto());
    } else {
      ComplaintDto newComplaint =
          new ComplaintDto(
              command.productId(),
              command.content(),
              command.creationDate(),
              command.creationUser());
      persistComplaintPort.persist(newComplaint);
    }
  }
}
