
package org.example.crtekup.Controller;

import org.example.crtekup.models.DemandeExamen;
import org.example.crtekup.models.Enseignant;
import org.example.crtekup.models.Etudiant;
import org.example.crtekup.service.DemandeExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    @GetMapping("/all")
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

    @GetMapping("/allDemandeCoursCertif")

    public ResponseEntity<List<DemandeExamen>> getAllDemandeCoursCertif() {
        System.out.println("Appel de l'API /allDemandeCoursCertif");
        try {
            List<DemandeExamen> demandeExamenCertif = demandeExamenService.getAllDemandeCoursCertif();
            return ResponseEntity.status(HttpStatus.OK).body(demandeExamenCertif);
        } catch (RuntimeException e) {
            System.out.println("Erreur dans l'appel de getAllDemandeCoursCertif : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<DemandeExamen>> rechercheDemandeNom(@RequestParam String coursName) {
        try {
            List<DemandeExamen> demandeExamenNom = demandeExamenService.rechercheDemandeNom(coursName);
            return ResponseEntity.status(HttpStatus.CREATED).body(demandeExamenNom);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/searchDate")
    public ResponseEntity<List<DemandeExamen>> rechercheDemandeDateCreation(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<DemandeExamen> demandeExamenNom = demandeExamenService.rechercheDemandeDateCreation(startDate, endDate);
            return ResponseEntity.status(HttpStatus.CREATED).body(demandeExamenNom);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

//    @GetMapping("/searchDemandNomEtudiant")
//    public List<DemandeExamen> getDemandesCertifByNomEtudiant(String nomEtudiant){
//     return demandeExamenService.getDemandesCertifByNomEtudiant(nomEtudiant);
//    }

}