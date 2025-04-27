package com.empik.complaintsmanagement.adapter.out.persistence;

import com.empik.complaintsmanagement.application.datatype.ComplaintDto;
import com.empik.complaintsmanagement.application.port.out.FindComplaintPort;
import com.empik.complaintsmanagement.application.port.out.PersistComplaintPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class ComplaintJpaAdapter implements FindComplaintPort, PersistComplaintPort {
//TODO: Integration test
  private final ComplaintRepository complaintRepository;
  private final ComplaintEntityMapper complaintEntityMapper;

  @Override
  public void persist(ComplaintDto complaint) {
    complaintRepository.save(complaintEntityMapper.toEntity(complaint));
  }

  @Override
  public Optional<ComplaintDto> findBy(Long productId, String creationUser) {
    return complaintRepository.findBy(productId, creationUser).map(complaintEntityMapper::toDto);
  }
}
