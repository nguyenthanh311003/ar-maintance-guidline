package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Subscription;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
  @Query(value = "SELECT s FROM Subscription s WHERE s.id = :id AND s.status = :status")
  Optional<Subscription> findByIdAndStatus(@Param("id") String id, @Param("status") String status);
}
