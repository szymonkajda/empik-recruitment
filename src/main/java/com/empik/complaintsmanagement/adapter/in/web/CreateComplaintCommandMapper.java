package com.empik.complaintsmanagement.adapter.in.web;

import com.empik.complaintsmanagement.application.datatype.IpAddress;
import com.empik.complaintsmanagement.application.port.in.CreateComplaintUseCase;
import com.empik.complaintsmanagement.openapi.model.ComplaintRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreateComplaintCommandMapper {

  @Mapping(target = "clientIp", source = "clientIp")
  CreateComplaintUseCase.Command toCommand(ComplaintRequest complaintRequest, IpAddress clientIp);
}
