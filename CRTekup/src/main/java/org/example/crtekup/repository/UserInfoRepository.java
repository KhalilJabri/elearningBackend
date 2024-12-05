package org.example.crtekup.repository;

import org.example.crtekup.models.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<Personne, Long> {
    Optional<Personne> findByEmail(String email);
    boolean existsByEmail(String email);
}
