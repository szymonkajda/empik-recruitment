package com.empik.complaintsmanagement.adapter.out.persistence;

import static jakarta.persistence.GenerationType.SEQUENCE;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "complaint")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintEntity {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "complaint_id_seq")
  private Long id;

  @Version private Long version;
  private Long productId;
  private String content;
  private OffsetDateTime creationDate;
  private String creationUser;
  private Integer counter;
  @Nullable private String countryName;
}
