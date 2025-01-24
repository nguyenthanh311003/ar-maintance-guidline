package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.ResultDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultDetailRepository extends JpaRepository<ResultDetail, String> {}
