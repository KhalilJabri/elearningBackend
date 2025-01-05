package org.example.crtekup.repository;

import org.example.crtekup.models.ERole;
import org.example.crtekup.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
     Role findByName(ERole name);
}
