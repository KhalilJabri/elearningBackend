package org.example.crtekup.Controller;

import org.example.crtekup.models.Cours;
import org.example.crtekup.models.Enseignant;
import org.example.crtekup.service.EnseignantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/api/enseignant")
public class EnseignantController {

    @Autowired
    private EnseignantService enseignantService;

    //Recupere l'Enseignant le plus poplular
    @GetMapping("/popular")
    public Enseignant viewMostPopularTeacher() {
return enseignantService.getMostPopularTeacher();


    }

@GetMapping("/all")
    public List<Enseignant> getAllEnseignants() {
        return enseignantService.getAllEnseignants();
    }
    @GetMapping("/{id}")
    public Enseignant getEnseignantById(@PathVariable  Long id) {
        return enseignantService.getEnseignantById(id);
    }

    @PostMapping("/addEnseignant")
    public ResponseEntity<Enseignant> ajouterEnseignant(@RequestBody Enseignant enseignant) {
        try {
            Enseignant savedEnseignant = enseignantService.ajouterEnseignant(enseignant);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEnseignant);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/updateEnseignant/{id}")
    public ResponseEntity<Enseignant> updateEnseignant(@PathVariable Long id, @RequestBody Enseignant updatedEnseignant) {
        try {
            Enseignant enseignant = enseignantService.updateEnseignant(id, updatedEnseignant);
            return ResponseEntity.ok(enseignant);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("deleteEnseignant/{id}")
    public ResponseEntity<Void> supprimerEnseignant(@PathVariable Long id) {
        try {
            enseignantService.supprimerEnseignant(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

}
