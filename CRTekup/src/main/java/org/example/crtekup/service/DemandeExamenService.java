package org.example.crtekup.service;

import org.example.crtekup.models.DemandeExamen;
import org.example.crtekup.models.EnumStatus;
import org.example.crtekup.models.Etudiant;
import org.example.crtekup.models.MyEnumType;
import org.example.crtekup.repository.DemandeExamenRepository;
import org.example.crtekup.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DemandeExamenService {

    @Autowired
    private DemandeExamenRepository demandeExamenRepository;
    @Autowired
    private EtudiantRepository etudiantRepository;
    // Méthode pour soumettre une demande de test blanc
    public DemandeExamen soumettreDemandeExamen(Long etudiantId, DemandeExamen demandeExamen) {
        // Vérifiez si l'étudiant existe
        Etudiant etudiant = etudiantRepository.findById(etudiantId).orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));

        // Remplir les champs de la demande avec l'étudiant
        demandeExamen.setEtudiantDemandeExamen(etudiant);
        demandeExamen.setCreated_at(LocalDate.now());  // La date de création est la date actuelle

        // Enregistrer la demande d'examen
        return demandeExamenRepository.save(demandeExamen);
    }

    // Récupérer toutes les demandes d'examen en cours
    public List<DemandeExamen> getAllDemandes() {
        return demandeExamenRepository.findAllByStatus(EnumStatus.EN_COURS);
    }

    // Valider une demande d'examen (changer le status à ACCEPTÉ)
    public Optional<DemandeExamen> validerDemande(Long demandeId) {
        Optional<DemandeExamen> demande = demandeExamenRepository.findById(demandeId);
        if (demande.isPresent()) {
            DemandeExamen d = demande.get();
            d.setStatus(EnumStatus.ACCEPTE); // Met à jour le statut à accepté
            demandeExamenRepository.save(d);
            return Optional.of(d);
        }
        return Optional.empty();
    }

    // Refuser une demande d'examen (changer le status à REFUSÉ)
    public Optional<DemandeExamen> refuserDemande(Long demandeId) {
        Optional<DemandeExamen> demande = demandeExamenRepository.findById(demandeId);
        if (demande.isPresent()) {
            DemandeExamen d = demande.get();
            d.setStatus(EnumStatus.REFUSE); // Met à jour le statut à refusé
            demandeExamenRepository.save(d);
            return Optional.of(d);
        }
        return Optional.empty();
    }

    // Récupérer toutes les demandes Certif
    public List<DemandeExamen> getAllDemandeCoursCertif() {
        return demandeExamenRepository.findAllDemandeCoursCertif(MyEnumType.CERTIF);
    }

    // Rechercher par nom de cours
    public List<DemandeExamen> rechercheDemandeNom(String coursName) {
        return demandeExamenRepository.findDemandesCertifByName(coursName,MyEnumType.CERTIF);
    }

    // Filtrer par date de création
    public List<DemandeExamen> rechercheDemandeDateCreation(LocalDate startDate, LocalDate endDate) {
        return demandeExamenRepository.findDemandesCertifByDate(startDate, endDate,MyEnumType.CERTIF);
    }

    // Filtrer par nom etudiant
    public List<DemandeExamen> getDemandesCertifByNomEtudiant(String nomEtudiant) {
        return demandeExamenRepository.findDemandeCertifByNameEtudiant(nomEtudiant,MyEnumType.CERTIF);
    }
}