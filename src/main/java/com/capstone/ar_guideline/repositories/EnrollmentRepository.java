package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, String> {
  Integer countByCourseId(String courseId);
}
