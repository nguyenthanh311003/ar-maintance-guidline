package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {}
