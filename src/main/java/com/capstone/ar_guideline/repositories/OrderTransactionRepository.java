package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.OrderTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTransactionRepository extends JpaRepository<OrderTransaction, String> {}
