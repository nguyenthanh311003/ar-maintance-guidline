package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByEmail(String email);

  @Query(value = "SELECT u FROM User u WHERE u.company.id = :companyId ORDER BY u.createdDate ASC")
  List<User> getUserByCompanyId(String companyId);
}
