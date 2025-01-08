package org.example.crtekup.repository;

import org.example.crtekup.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    // Recherche d'un Admin par son ID
    Optional<Admin> findById(Long id);
}
