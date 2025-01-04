
package org.example.crtekup.repository;

import org.example.crtekup.models.DemandeExamen;
import org.example.crtekup.models.EnumStatus;
import org.example.crtekup.models.MyEnumType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DemandeExamenRepository extends JpaRepository<DemandeExamen, Long> {
    List<DemandeExamen> findAllByStatus(EnumStatus status);

    Optional<DemandeExamen> findById(Long id);

    @Query("SELECT d FROM DemandeExamen d WHERE d.type = :type")
    List<DemandeExamen>  findAllDemandeCoursCertif(@Param("type") MyEnumType type);

    @Query("SELECT d FROM DemandeExamen d WHERE LOWER(d.coursName) LIKE LOWER(CONCAT('%', :coursName, '%')) AND d.type = :type")
    List<DemandeExamen> findDemandesCertifByName(@Param("coursName") String coursName, @Param("type") MyEnumType type);

    @Query("SELECT d FROM DemandeExamen d WHERE d.created_at BETWEEN :startDate AND :endDate AND d.type = :type")
    List<DemandeExamen> findDemandesCertifByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,@Param("type") MyEnumType type);

    @Query("SELECT d FROM DemandeExamen d WHERE d.etudiantDemandeExamen.firstName LIKE %:nomEtudiant% AND d.type = :type")
    List<DemandeExamen> findDemandeCertifByNameEtudiant(@Param("nomEtudiant") String nomEtudiant,@Param("type") MyEnumType type);
}