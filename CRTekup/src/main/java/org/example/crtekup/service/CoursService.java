
package org.example.crtekup.service;

import org.example.crtekup.models.Admin;
import org.example.crtekup.models.Cours;
import org.example.crtekup.repository.AdminRepository;
import org.example.crtekup.repository.CoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoursService {

    @Autowired
    private CoursRepository coursRepository;
    @Autowired
    private AdminRepository adminRepository;

    public Cours ajouterCours(Cours cours, Long adminId) {
        // Vérifier si l'admin existe
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin non trouvé"));

        // Assigner l'admin au cours
        cours.setAdminCours(admin);

        // Sauvegarder le cours
        return coursRepository.save(cours);
    }

    // Modifier un cours
    public Cours updateCours(Long id, Cours coursDetails) {
        Cours cours = coursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé"));

        // Mise à jour des détails du cours
        cours.setName(coursDetails.getName());
        cours.setDescription(coursDetails.getDescription());
        cours.setAvailable(coursDetails.isAvailable());
        cours.setRate(coursDetails.getRate());

        return coursRepository.save(cours);
    }

    // Méthode pour supprimer un cours
    public void supprimerCours(Long id) {
        Cours cours = coursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé"));

        coursRepository.delete(cours);
    }
    // Récupérer un cours par ID
    public Cours getCours(Long id) {
        return coursRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cours non trouvé"));
    }

    // Récupérer tous les cours
    public List<Cours> getAllCours() {
        return coursRepository.findAll();
    }

}