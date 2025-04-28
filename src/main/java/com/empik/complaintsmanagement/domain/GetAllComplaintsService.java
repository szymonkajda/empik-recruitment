package com.empik.complaintsmanagement.domain;

import com.empik.complaintsmanagement.application.datatype.ComplaintDto;
import com.empik.complaintsmanagement.application.port.in.GetAllComplaintsQuery;
import com.empik.complaintsmanagement.application.port.out.FindComplaintPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class GetAllComplaintsService implements GetAllComplaintsQuery {

  private final FindComplaintPort findComplaintPort;

  @Override
  @Transactional(readOnly = true)
  public List<ComplaintDto> get() {
    return findComplaintPort.findAll();
  }
}
