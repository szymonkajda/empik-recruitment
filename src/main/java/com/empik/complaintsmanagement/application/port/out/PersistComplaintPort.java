package com.empik.complaintsmanagement.application.port.out;

import com.empik.complaintsmanagement.application.datatype.ComplaintDto;

public interface PersistComplaintPort {

  void persist(ComplaintDto complaint);
}
