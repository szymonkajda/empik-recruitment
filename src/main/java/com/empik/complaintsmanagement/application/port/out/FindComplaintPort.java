package com.empik.complaintsmanagement.application.port.out;

import com.empik.complaintsmanagement.application.datatype.ComplaintDto;
import java.util.Optional;

public interface FindComplaintPort {

  Optional<ComplaintDto> findBy(Long productId, String creationUser);
}
