
package org.example.crtekup.Controller;

import org.example.crtekup.models.DemandeExamen;
import org.example.crtekup.models.Etudiant;
import org.example.crtekup.service.DemandeExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/demandes-examen")
public class DemandeExamenController {

    @Autowired
    private DemandeExamenService demandeExamenService;

    // Endpoint pour soumettre une demande d'examen
    @PostMapping("/soumettre/{etudiantId}")
    public ResponseEntity<DemandeExamen> soumettreDemande(@PathVariable Long etudiantId,
                                                          @RequestBody DemandeExamen demandeExamen) {
        DemandeExamen createdDemandeExamen = demandeExamenService.soumettreDemandeExamen(etudiantId, demandeExamen);
        return ResponseEntity.ok(createdDemandeExamen);  // Retourner la demande créée
    }



    // Endpoint pour récupérer toutes les demandes d'examen en cours
    @GetMapping
    public List<DemandeExamen> getAllDemandes() {
        return demandeExamenService.getAllDemandes();
    }

    // Endpoint pour valider une demande
    @PutMapping("/{id}/valider")
    public ResponseEntity<String> validerDemande(@PathVariable("id") Long id) {
        Optional<DemandeExamen> demande = demandeExamenService.validerDemande(id);
        if (demande.isPresent()) {
            return ResponseEntity.ok("Demande validée");
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint pour refuser une demande
    @PutMapping("/{id}/refuser")
    public ResponseEntity<String> refuserDemande(@PathVariable("id") Long id) {
        Optional<DemandeExamen> demande = demandeExamenService.refuserDemande(id);
        if (demande.isPresent()) {
            return ResponseEntity.ok("Demande refusée");
        }
        return ResponseEntity.notFound().build();
    }
}