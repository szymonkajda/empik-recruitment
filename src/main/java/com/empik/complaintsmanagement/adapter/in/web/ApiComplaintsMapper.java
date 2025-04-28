package com.empik.complaintsmanagement.adapter.in.web;

import com.empik.complaintsmanagement.application.datatype.ComplaintDto;
import com.empik.complaintsmanagement.openapi.model.Complaint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApiComplaintsMapper {

  Complaint toApi(ComplaintDto complaintDto);
}
