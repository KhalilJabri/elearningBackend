
package org.example.crtekup.repository;

import org.example.crtekup.models.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    @Query("SELECT e FROM Enseignant e LEFT JOIN e.demandeCoursList d GROUP BY e.id ORDER BY COUNT(d) DESC")
    List<Enseignant> findMostPopularTeacher();
}
