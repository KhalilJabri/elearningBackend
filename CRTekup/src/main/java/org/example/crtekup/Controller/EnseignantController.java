package org.example.crtekup.Controller;

import org.example.crtekup.models.Enseignant;
import org.example.crtekup.service.EnseignantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enseignant")
public class EnseignantController {

    private EnseignantService enseignantService;

    //Recupere l'Enseignant le plus poplular
    @GetMapping("/popular")
    public ResponseEntity<Enseignant> viewMostPopularTeacher() {
        Enseignant popularTeachers = enseignantService.getMostPopularTeacher();
        return ResponseEntity.ok(popularTeachers);
    }

    @GetMapping
    public List<Enseignant> getAllEnseignants() {
        return enseignantService.getAllEnseignants();
    }

    @GetMapping("/{id}")
    public Enseignant getEnseignantById(@PathVariable Long id) {
        return enseignantService.getEnseignantById(id);
    }

    @PostMapping
    public Enseignant ajouterEnseignant(@RequestBody Enseignant enseignant) {
        return enseignantService.ajouterEnseignant(enseignant);
    }

    @PutMapping("/{id}")
    public Enseignant updateEnseignant(@PathVariable Long id, @RequestBody Enseignant updatedEnseignant) {
        return enseignantService.updateEnseignant(id, updatedEnseignant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerEnseignant(@PathVariable Long id) {
        try {
            enseignantService.supprimerEnseignant(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }


}
