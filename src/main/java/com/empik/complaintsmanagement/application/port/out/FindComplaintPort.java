package com.empik.complaintsmanagement.application.port.out;

import com.empik.complaintsmanagement.application.datatype.ComplaintDto;
import java.util.List;
import java.util.Optional;

public interface FindComplaintPort {

  Optional<ComplaintDto> findBy(Long productId, String creationUser);

  Optional<ComplaintDto> findBy(Long complaintId);

  List<ComplaintDto> findAll();
}
