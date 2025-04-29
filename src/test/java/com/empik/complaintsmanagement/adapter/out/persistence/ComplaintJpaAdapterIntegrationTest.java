package com.empik.complaintsmanagement.adapter.out.persistence;

import static com.empik.complaintsmanagement.application.datatype.ComplaintTestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import com.empik.complaintsmanagement.application.datatype.ComplaintDto;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("test")
@SpringBootTest
class ComplaintJpaAdapterIntegrationTest {

  @Autowired private ComplaintJpaAdapter complaintJpaAdapter;
  @Autowired private ComplaintRepository complaintRepository;
  @Autowired private JdbcTemplate jdbcTemplate;

  @AfterEach
  public void cleanUp() {
    jdbcTemplate.execute("RUNSCRIPT FROM 'classpath:/datasets/clean-up-dataset.sql'");
  }

  @Test
  void shouldNewPersistComplaintCorrectly() {
    // given
    ComplaintDto complaint = createNewComplaint();

    // when
    complaintJpaAdapter.persist(complaint);

    // then
    Iterable<ComplaintEntity> complaints = complaintRepository.findAll();
    assertThat(complaints)
        .hasSize(1)
        .first()
        .hasNoNullFieldsOrProperties()
        .usingRecursiveComparison()
        .ignoringFields("id", "version", "creationDate")
        .isEqualTo(createNewComplaint());
  }

  @Test
  @Sql(value = "/datasets/complaints-dataset.sql")
  void shouldFindComplaintById() {
    // given + when
    ComplaintDto complaintDto = complaintJpaAdapter.findBy(10L).orElseThrow();

    // then
    assertThat(complaintDto)
        .hasNoNullFieldsOrProperties()
        .usingRecursiveComparison()
        .ignoringFields("creationDate")
        .isEqualTo(createExistingComplaint());
  }

  @Test
  @Sql(value = "/datasets/complaints-dataset.sql")
  void shouldFindComplaintByProductIdAndUser() {
    // given + when
    ComplaintDto complaintDto = complaintJpaAdapter.findBy(PRODUCT_ID, CREATION_USER).orElseThrow();

    // then
    assertThat(complaintDto)
        .hasNoNullFieldsOrProperties()
        .usingRecursiveComparison()
        .ignoringFields("creationDate")
        .isEqualTo(createExistingComplaint());
  }

  @Test
  @Sql(value = "/datasets/complaints-dataset.sql", executionPhase = BEFORE_TEST_METHOD)
  void shouldFindAllComplaints() {
    // given + when
    List<ComplaintDto> complaints = complaintJpaAdapter.findAll();

    // then
    assertThat(complaints)
        .hasSize(4)
        .allSatisfy(complaintDto -> assertThat(complaintDto).hasNoNullFieldsOrProperties());
  }
}
