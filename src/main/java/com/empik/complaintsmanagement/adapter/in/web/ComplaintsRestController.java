package com.empik.complaintsmanagement.adapter.in.web;

import com.empik.complaintsmanagement.api.ComplaintsApi;
import com.empik.complaintsmanagement.application.port.in.CreateComplaintUseCase;
import com.empik.complaintsmanagement.openapi.model.ComplaintRequest;
import com.empik.complaintsmanagement.openapi.model.ComplaintsCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1")
class ComplaintsRestController implements ComplaintsApi {
//TODO: Integration test
  private final CreateComplaintUseCase createComplaintUseCase;
  private final CreateComplaintCommandMapper createComplaintCommandMapper;

  @Override
  public ResponseEntity<Void> createComplaint(ComplaintRequest complaintRequest) {
    createComplaintUseCase.create(createComplaintCommandMapper.toCommand(complaintRequest));
    return ResponseEntity.status(CREATED).build();
  }

  @Override
  public ResponseEntity<Void> updateComplaintContent(Long id, String complaintContent) {
    // TODO
    return ComplaintsApi.super.updateComplaintContent(id, complaintContent);
  }

  @Override
  public ResponseEntity<ComplaintsCollection> getAllComplaints() {
    return ComplaintsApi.super.getAllComplaints();
  }
}
