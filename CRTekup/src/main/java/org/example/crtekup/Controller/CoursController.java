
package org.example.crtekup.Controller;

import org.example.crtekup.models.Cours;
import org.example.crtekup.service.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cours")
public class CoursController {

    @Autowired
    private CoursService coursService;

    @PostMapping("/add")
    public ResponseEntity<Cours> ajouterCours(@RequestBody Cours cours, @RequestParam Long adminId) {
        try {
            Cours savedCours = coursService.ajouterCours(cours, adminId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCours);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Route pour mettre à jour un cours
    @PutMapping("/update/{id}")
    public ResponseEntity<Cours> updateCours(@PathVariable Long id, @RequestBody Cours coursDetails) {
        try {
            Cours updatedCours = coursService.updateCours(id, coursDetails);
            return ResponseEntity.ok(updatedCours);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Route pour supprimer un cours
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> supprimerCours(@PathVariable Long id) {
        try {
            coursService.supprimerCours(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cours> getCoursById(@PathVariable Long id) {
        Cours cours = coursService.getCours(id);
        return ResponseEntity.ok(cours);
    }

    // Récupérer tous les cours
    @GetMapping("/all")
    public List<Cours> getAllCours() {
        return coursService.getAllCours();
    }
}