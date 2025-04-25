package com.empik.complaintsmanagement.adapter.in.web;

import com.empik.complaintsmanagement.api.ComplaintsApi;
import com.empik.complaintsmanagement.openapi.model.ComplaintContent;
import com.empik.complaintsmanagement.openapi.model.ComplaintRequest;
import com.empik.complaintsmanagement.openapi.model.ComplaintsCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1")
class ComplaintsRestController implements ComplaintsApi {

  @Override
  public ResponseEntity<Void> createComplaint(ComplaintRequest complaintRequest) {
    // TODO
    return ComplaintsApi.super.createComplaint(complaintRequest);
  }

  @Override
  public ResponseEntity<Void> updateComplaintContent(
      Integer id, ComplaintContent complaintContent) {
    // TODO
    return ComplaintsApi.super.updateComplaintContent(id, complaintContent);
  }

  @Override
  public ResponseEntity<ComplaintsCollection> getAllComplaints() {
    return ComplaintsApi.super.getAllComplaints();
  }
}
