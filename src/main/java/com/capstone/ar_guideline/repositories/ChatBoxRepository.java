package com.capstone.ar_guideline.repositories;


import com.capstone.ar_guideline.entities.ChatBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatBoxRepository extends JpaRepository<ChatBox, UUID> {

}