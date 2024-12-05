package org.example.crtekup.bootstrap;

import org.example.crtekup.models.ERole;
import org.example.crtekup.models.Role;
import org.example.crtekup.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Vérifie si les rôles existent déjà, sinon les insère
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
            roleRepository.save(new Role(ERole.ROLE_ETUDIANT));
            roleRepository.save(new Role(ERole.ROLE_ENSEIGNANT));
            System.out.println("Rôles insérés avec succès");
        }
    }
}
