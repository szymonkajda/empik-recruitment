package com.empik.complaintsmanagement.adapter.out.persistence;

import com.empik.complaintsmanagement.application.datatype.ComplaintDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComplaintEntityMapper {

  ComplaintDto toDto(ComplaintEntity complaintEntity);

  ComplaintEntity toEntity(ComplaintDto complaint);
}
