package com.empik.complaintsmanagement.domain;

import static com.empik.complaintsmanagement.application.datatype.ComplaintTestDataFactory.COMPLAINT_ID;
import static com.empik.complaintsmanagement.application.datatype.ComplaintTestDataFactory.createExistingComplaint;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.empik.complaintsmanagement.application.datatype.ComplaintDto;
import com.empik.complaintsmanagement.application.port.in.UpdateComplaintContentUseCase;
import com.empik.complaintsmanagement.application.port.out.FindComplaintPort;
import com.empik.complaintsmanagement.application.port.out.PersistComplaintPort;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.stream.Stream;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateComplaintContentServiceTest {

  @Mock private FindComplaintPort findComplaintPort;
  @Mock private PersistComplaintPort persistComplaintPort;
  @InjectMocks private UpdateComplaintContentService updateComplaintContentService;
  @Captor private ArgumentCaptor<ComplaintDto> complaintCaptor;

  @ParameterizedTest
  @ValueSource(strings = {"New content", "New.content!", "  123", "  123  "})
  void shouldUpdateComplaintContent(String newContent) {
    // given
    given(findComplaintPort.findBy(COMPLAINT_ID))
        .willReturn(Optional.of(createExistingComplaint()));

    UpdateComplaintContentUseCase.Command command =
        new UpdateComplaintContentUseCase.Command(COMPLAINT_ID, newContent);

    // when
    updateComplaintContentService.update(command);

    // then
    verify(persistComplaintPort).persist(complaintCaptor.capture());
    ComplaintDto complaint = complaintCaptor.getValue();

    assertThat(complaint)
        .usingRecursiveComparison()
        .ignoringFields("content")
        .isEqualTo(createExistingComplaint());
    assertThat(complaint.content()).isEqualTo(newContent);
  }

  private static Stream<String> invalidComplaintContentData() {
    return Stream.of(null, "     ", "");
  }

  @ParameterizedTest
  @MethodSource("invalidComplaintContentData")
  void shouldThrowExceptionWhenNewContentIsWrong(String newContent) {
    // given + when
    AbstractThrowableAssert<?, ? extends Throwable> thrownException =
        assertThatThrownBy(
            () -> new UpdateComplaintContentUseCase.Command(COMPLAINT_ID, newContent));

    // then
    thrownException
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Content must not be empty.");
  }

  @Test
  void shouldThrowExceptionWhenComplaintNotFound() {
    // given
    given(findComplaintPort.findBy(COMPLAINT_ID)).willReturn(Optional.empty());

    UpdateComplaintContentUseCase.Command command =
        new UpdateComplaintContentUseCase.Command(COMPLAINT_ID, "newContent");

    // when
    AbstractThrowableAssert<?, ? extends Throwable> thrownException =
        assertThatThrownBy(() -> updateComplaintContentService.update(command));

    // then
    thrownException
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage("Complaint does not exist.");
  }
}
