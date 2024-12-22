package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
  Optional<Role> findByRoleName(String name);
}
