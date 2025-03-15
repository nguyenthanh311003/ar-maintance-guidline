package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.ServicePrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicePricerRepository extends JpaRepository<ServicePrice, String> {
  boolean existsByName(String name);
}
