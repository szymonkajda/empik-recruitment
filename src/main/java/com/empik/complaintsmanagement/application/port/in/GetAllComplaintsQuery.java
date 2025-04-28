package com.empik.complaintsmanagement.application.port.in;

import com.empik.complaintsmanagement.application.datatype.ComplaintDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface GetAllComplaintsQuery {

  @Transactional(readOnly = true)
  List<ComplaintDto> get();
}
