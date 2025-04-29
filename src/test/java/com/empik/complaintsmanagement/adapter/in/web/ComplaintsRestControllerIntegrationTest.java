package com.empik.complaintsmanagement.adapter.in.web;

import static com.empik.complaintsmanagement.application.datatype.ComplaintTestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.empik.complaintsmanagement.openapi.model.ComplaintRequest;
import com.empik.complaintsmanagement.openapi.model.ComplaintsCollection;
import com.empik.complaintsmanagement.openapi.model.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ComplaintsRestControllerIntegrationTest {

  private static final String API_BASE = "/api/v1";
  private static final String COMPLAINTS_API = API_BASE + "/complaints";
  private static final String UPDATE_CONTENT_API = COMPLAINTS_API + "/%s/updateContent";
  private static final String JSON_CONTENT_TYPE = "application/json";
  public static final long NOT_EXISTING_COMPLAINT_ID = 150L;

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private JdbcTemplate jdbcTemplate;

  @AfterEach
  public void cleanUp() {
    jdbcTemplate.execute("RUNSCRIPT FROM 'classpath:/datasets/clean-up-dataset.sql'");
  }

  @Test
  void shouldSuccessfullyCallCreateComplaintRequest() throws Exception {
    // given
    ComplaintRequest complaintRequest =
        new ComplaintRequest()
            .productId(PRODUCT_ID)
            .content(CONTENT)
            .creationDate(CREATION_DATE)
            .creationUser(CREATION_USER);
    String complaintRequestJson = objectMapper.writeValueAsString(complaintRequest);

    // when
    ResultActions actionResult =
        mockMvc.perform(
            put(COMPLAINTS_API).contentType(JSON_CONTENT_TYPE).content(complaintRequestJson));

    // then
    actionResult.andExpect(status().isCreated());
  }

  @Test
  @Sql(value = "/datasets/complaints-dataset.sql")
  void shouldSuccessfullyCallUpdateComplaintContentRequest() throws Exception {
    // given
    String complaintContentJson = objectMapper.writeValueAsString("NewComplaintContent");

    // when
    ResultActions actionResult =
        mockMvc.perform(
            post(UPDATE_CONTENT_API.formatted(COMPLAINT_ID))
                .contentType(JSON_CONTENT_TYPE)
                .content(complaintContentJson));

    // then
    actionResult.andExpect(status().isOk());
  }

  @Test
  @Sql(value = "/datasets/complaints-dataset.sql")
  void shouldSuccessfullyCallGetAllComplaintsRequest() throws Exception {
    // given + when
    ResultActions actionResult = mockMvc.perform(get(COMPLAINTS_API));

    // then
    String resultString =
        actionResult.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    ComplaintsCollection complaintsCollection =
        objectMapper.readValue(resultString, ComplaintsCollection.class);
    assertThat(complaintsCollection.getComplaints()).hasSize(4);
  }

  @Test
  void shouldReturnBadRequestWhenUpdatingComplaintWithEmptyContent() throws Exception {
    // given + when
    ResultActions actionResult =
        mockMvc.perform(
            post(UPDATE_CONTENT_API.formatted(COMPLAINT_ID))
                .contentType(JSON_CONTENT_TYPE)
                .content("   "));

    // then
    String resultString =
        actionResult
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();
    ErrorResponse errorResponse = objectMapper.readValue(resultString, ErrorResponse.class);
    assertThat(errorResponse.getMessage()).isEqualTo("Content must not be empty.");
  }

  @Test
  void shouldReturnNotFoundWhenUpdatingComplaintThatDoesNotExist() throws Exception {
    // given
    String complaintContentJson = objectMapper.writeValueAsString(null);

    // when
    ResultActions actionResult =
        mockMvc.perform(
            post(UPDATE_CONTENT_API.formatted(NOT_EXISTING_COMPLAINT_ID))
                .contentType(JSON_CONTENT_TYPE)
                .content(complaintContentJson));

    // then
    String resultString =
        actionResult
            .andExpect(status().isNotFound())
            .andReturn()
            .getResponse()
            .getContentAsString();
    ErrorResponse errorResponse = objectMapper.readValue(resultString, ErrorResponse.class);
    assertThat(errorResponse.getMessage()).isEqualTo("Complaint does not exist.");
  }
}
