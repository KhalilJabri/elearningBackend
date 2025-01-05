package org.example.crtekup.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.crtekup.models.Enseignant;
import org.example.crtekup.repository.EnseignantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnseignantService {

    @Autowired
    private EnseignantRepository enseignantRepository;

    // Recuepere listes des Enseignants
    public List<Enseignant> getAllEnseignants() {
        return enseignantRepository.findAll();
    }

    //Recupere Enseignant by ID
    public Enseignant getEnseignantById(Long id) {
        return enseignantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enseignant not found with ID: " + id));
    }

    //Ajouter Enseignant
    public Enseignant ajouterEnseignant(Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }
    //update Enseignant
    public Enseignant updateEnseignant(Long id, Enseignant updatedEnseignant) {
        Enseignant existingEnseignant = getEnseignantById(id);
        existingEnseignant.setMatiere(updatedEnseignant.getMatiere());
        // Ajoutez ici d'autres champs si nécessaire
        return enseignantRepository.save(existingEnseignant);
    }
    //delete Enseignant
    public void supprimerEnseignant(Long id) {
        enseignantRepository.deleteById(id);
    }
    // Recueper l'Enseignant le plus popular
    public Enseignant getMostPopularTeacher() {
        List<Enseignant> enseignants = enseignantRepository.findMostPopularTeacher();
        return enseignants.isEmpty() ? null : enseignants.get(0);
    }
}
