package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByEmail(String email);

  @Query(value = "SELECT u FROM User u WHERE u.phone = :phone")
  List<User> findByPhone(@Param("phone") String phone);

  @Query(
      value =
          "SELECT u FROM User u WHERE u.email = :email AND u.status = :status AND u.role.roleName = 'COMPANY'")
  User findByEmailAndStatus(String email, String status);

  //  @Query(
  //      "SELECT u FROM User u "
  //          + "WHERE u.company.id = :companyId "
  //          + "AND u.role.roleName = 'STAFF' "
  //          + "AND (:keyword IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) "
  //          + "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))) "
  //          + "AND (:isAssign = '' OR "
  //          + "ORDER BY u.createdDate ASC")
  //  List<User> getUserByCompanyId(
  //      Pageable pageable,
  //      @Param("companyId") String companyId,
  //      @Param("keyword") String keyword,
  //      @Param("isAssign") String isAssign,
  //      @Param("courseId") String courseId);

  //  @Query(
  //      "SELECT COUNT(u) FROM User u "
  //          + "WHERE u.company.id = :companyId "
  //          + "AND u.role.roleName = 'STAFF' "
  //          + "AND (:keyword IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) "
  //          + "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))) "
  //          + "AND (:isAssign = '' OR "
  //          + "( :isAssign = 'NotIsAssign' AND e.id IS NULL ) OR "
  //          + "( :isAssign = 'IsAssign' AND e.id IS NOT NULL )) "
  //          + "ORDER BY u.createdDate ASC")
  //  int countUsersByCompanyId(
  //      @Param("companyId") String companyId,
  //      @Param("keyword") String keyword,
  //      @Param("isAssign") String isAssign,
  //      @Param("courseId") String courseId);

  @Query(
      value =
          "SELECT u FROM User u WHERE "
              + "(:email = '' OR :email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) "
              + "AND (:status IS NULL OR :status = '' OR u.status = :status) "
              + "ORDER BY u.createdDate DESC")
  Page<User> getUsers(
      Pageable pageable, @Param("email") String email, @Param("status") String status);

  @Query(
      value =
          "SELECT u FROM User u WHERE u.company.id = :companyId AND u.role.roleName = 'STAFF' "
              + "AND ("
              + "    (:status IS NULL OR :status = '' AND (u.status = 'ACTIVE' OR u.status = 'INACTIVE')) "
              + "    OR (:status = 'ACTIVE' AND u.status = 'ACTIVE') "
              + "    OR (:status = 'INACTIVE' AND u.status = 'INACTIVE') "
              + ") "
              + "AND (:phoneNumber IS NULL OR :phoneNumber = '' OR LOWER(u.phone) LIKE LOWER(CONCAT('%', :phoneNumber, '%'))) "
              + "AND (:email IS NULL OR :email = '' OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) "
              + "ORDER BY u.createdDate DESC")
  Page<User> getStaffByCompanyId(
      Pageable pageable,
      @Param("companyId") String companyId,
      @Param("phoneNumber") String phoneNumber,
      @Param("email") String email,
      @Param("status") String status);

  List<User> findByCompanyId(String companyId);

  User findUserByCompanyId(String id);

  @Query(
      value =
          "SELECT u FROM User u WHERE u.company.id = :companyId AND u.role.roleName = 'COMPANY' AND u.status = 'ACTIVE'")
  User findUserByCompanyIdAndAdminRole(String companyId);

  @Query(
      value =
          "SELECT COUNT(u) FROM User u WHERE (u.company.id = :companyId OR :companyId IS NULL) AND (u.status = :status OR u.status IS NULL )")
  Integer countAllBy(String companyId, String status);

  @Query(
      value =
          "SELECT COUNT(u) FROM User u WHERE (u.company.id = :companyId OR :companyId IS NULL) AND (u.status = :status OR u.status IS NULL ) AND u.role.roleName = 'STAFF'")
  Integer countAllForCompanyBy(String companyId, String status);

  Long countByCompany_Id(String companyId);

  @Query(
      value =
          "SELECT u FROM User u WHERE u.company.id = :companyId AND u.role.roleName = 'COMPANY' AND u.status = 'ACTIVE' ")
  List<User> findCompanyAdminByCompanyId(String companyId);

  User findUserById(String id);

  @Query(
      value =
          "SELECT COUNT(u) FROM User u WHERE u.company.id = :companyId AND u.role.roleName = 'STAFF' AND u.status = 'ACTIVE' GROUP BY u.company.id")
  Integer countByCompany_IdAndStatus(String companyId);
}
