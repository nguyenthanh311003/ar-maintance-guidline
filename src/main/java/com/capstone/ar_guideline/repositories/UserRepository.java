package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByEmail(String email);

  @Query(
      "SELECT u FROM User u "
          + "LEFT JOIN Enrollment e ON u.id = e.user.id AND e.course.id = :courseId "
          + "WHERE u.company.id = :companyId "
          + "AND (:keyword IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) "
          + "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))) "
          + "AND (:isAssign = '' OR "
          + "( :isAssign = 'NotIsAssign' AND e.id IS NULL ) OR "
          + "( :isAssign = 'IsAssign' AND e.id IS NOT NULL )) "
          + "ORDER BY u.createdDate ASC")
  List<User> getUserByCompanyId(
      Pageable pageable,
      @Param("companyId") String companyId,
      @Param("keyword") String keyword,
      @Param("isAssign") String isAssign,
      @Param("courseId") String courseId);
}
