package com.empik.complaintsmanagement.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ComplaintRepository extends CrudRepository<ComplaintEntity, Long> {

  @Query(
      "SELECT c from ComplaintEntity c where c.productId = :productId AND c.creationUser = :creationUser")
  Optional<ComplaintEntity> findBy(Long productId, String creationUser);
}
