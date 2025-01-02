
package org.example.crtekup.repository;

import org.example.crtekup.models.DemandeExamen;
import org.example.crtekup.models.EnumStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DemandeExamenRepository extends JpaRepository<DemandeExamen, Long> {
    List<DemandeExamen> findAllByStatus(EnumStatus status);

    Optional<DemandeExamen> findById(Long id);
}