package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResultRepository extends JpaRepository<Result, String> {
}
