package com.empik.complaintsmanagement.adapter.in.web;

import static com.empik.complaintsmanagement.adapter.in.web.IpResolver.resolveIp;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.empik.complaintsmanagement.api.ComplaintsApi;
import com.empik.complaintsmanagement.application.datatype.IpAddress;
import com.empik.complaintsmanagement.application.port.in.CreateComplaintUseCase;
import com.empik.complaintsmanagement.application.port.in.GetAllComplaintsQuery;
import com.empik.complaintsmanagement.application.port.in.UpdateComplaintContentUseCase;
import com.empik.complaintsmanagement.openapi.model.Complaint;
import com.empik.complaintsmanagement.openapi.model.ComplaintRequest;
import com.empik.complaintsmanagement.openapi.model.ComplaintsCollection;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1")
class ComplaintsRestController implements ComplaintsApi {

  private final HttpServletRequest httpServletRequest;
  private final CreateComplaintUseCase createComplaintUseCase;
  private final UpdateComplaintContentUseCase updateComplaintContentUseCase;
  private final GetAllComplaintsQuery getAllComplaintsQuery;
  private final CreateComplaintCommandMapper createComplaintCommandMapper;
  private final ApiComplaintsMapper complaintsMapper;

  @Override
  public ResponseEntity<Void> createComplaint(ComplaintRequest complaintRequest) {
    IpAddress clientIp = resolveIp(httpServletRequest);
    createComplaintUseCase.create(
        createComplaintCommandMapper.toCommand(complaintRequest, clientIp));
    return ResponseEntity.status(CREATED).build();
  }

  @Override
  public ResponseEntity<Void> updateComplaintContent(Long id, String complaintContent) {
    UpdateComplaintContentUseCase.Command command =
        new UpdateComplaintContentUseCase.Command(id, complaintContent);
    updateComplaintContentUseCase.update(command);
    return ResponseEntity.status(OK).build();
  }

  @Override
  public ResponseEntity<ComplaintsCollection> getAllComplaints() {
    List<Complaint> complaints =
        getAllComplaintsQuery.get().stream().map(complaintsMapper::toApi).toList();
    return ResponseEntity.status(OK).body(new ComplaintsCollection(complaints));
  }
}
