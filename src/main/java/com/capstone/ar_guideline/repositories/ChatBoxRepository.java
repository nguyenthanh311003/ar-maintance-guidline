package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.ChatBox;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatBoxRepository extends JpaRepository<ChatBox, UUID> {}
