package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.ModelType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelTypeRepository extends JpaRepository<ModelType, String> {
    @Query(value = "SELECT mt FROM ModelType mt ORDER BY mt.createdDate ASC")
    Page<ModelType> getAlL(Pageable pageable);
}
