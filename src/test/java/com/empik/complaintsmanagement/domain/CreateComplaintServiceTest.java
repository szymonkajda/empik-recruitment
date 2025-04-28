package com.empik.complaintsmanagement.domain;

import static com.empik.complaintsmanagement.application.datatype.ComplaintTestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.empik.complaintsmanagement.application.datatype.ComplaintDto;
import com.empik.complaintsmanagement.application.port.in.CreateComplaintUseCase;
import com.empik.complaintsmanagement.application.port.out.FindComplaintPort;
import com.empik.complaintsmanagement.application.port.out.PersistComplaintPort;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateComplaintServiceTest {

  @Mock private FindComplaintPort findComplaintPort;
  @Mock private PersistComplaintPort persistComplaintPort;
  @InjectMocks private CreateComplaintService createComplaintService;
  @Captor private ArgumentCaptor<ComplaintDto> complaintCaptor;

  @Test
  void shouldAddNewComplaint() {
    // given
    given(findComplaintPort.findBy(PRODUCT_ID, CREATION_USER)).willReturn(Optional.empty());

    CreateComplaintUseCase.Command command =
        new CreateComplaintUseCase.Command(PRODUCT_ID, CONTENT, CREATION_DATE, CREATION_USER);

    // when
    createComplaintService.create(command);

    // then
    verify(persistComplaintPort).persist(complaintCaptor.capture());
    ComplaintDto complaint = complaintCaptor.getValue();

    assertThat(complaint)
        .usingRecursiveComparison()
        .ignoringFields("id", "version", "counter")
        .isEqualTo(createExistingComplaint());
    assertThat(complaint.id()).isNull();
    assertThat(complaint.version()).isNull();
    assertThat(complaint.counter()).isEqualTo(1);
  }

  @Test
  void shouldIncrementComplaintCounterWhenComplaintExists() {
    // given
    given(findComplaintPort.findBy(PRODUCT_ID, CREATION_USER))
        .willReturn(Optional.of(createExistingComplaint()));

    CreateComplaintUseCase.Command command =
        new CreateComplaintUseCase.Command(PRODUCT_ID, CONTENT, CREATION_DATE, CREATION_USER);

    // when
    createComplaintService.create(command);

    // then
    verify(persistComplaintPort).persist(complaintCaptor.capture());
    ComplaintDto complaint = complaintCaptor.getValue();

    assertThat(complaint)
        .usingRecursiveComparison()
        .ignoringFields("counter")
        .isEqualTo(createExistingComplaint());
    assertThat(complaint.counter()).isEqualTo(COUNTER + 1);
  }
}
