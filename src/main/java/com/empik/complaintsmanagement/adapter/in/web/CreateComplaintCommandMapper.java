package com.empik.complaintsmanagement.adapter.in.web;

import com.empik.complaintsmanagement.application.port.in.CreateComplaintUseCase;
import com.empik.complaintsmanagement.openapi.model.ComplaintRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateComplaintCommandMapper {

  CreateComplaintUseCase.Command toCommand(ComplaintRequest complaintRequest);
}
