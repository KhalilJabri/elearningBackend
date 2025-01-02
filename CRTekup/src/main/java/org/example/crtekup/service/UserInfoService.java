package org.example.crtekup.service;

import org.example.crtekup.models.*;
import org.example.crtekup.repository.RoleRepository;
import org.example.crtekup.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    @Lazy
    private PasswordEncoder encoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Personne personne = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        List<SimpleGrantedAuthority> authorities = personne.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .toList();

        return new org.springframework.security.core.userdetails.User(
                personne.getEmail(),
                personne.getPassword(),
                authorities
        );
    }



    public String addUser(Personne personne) {
        // Encode le mot de passe
        personne.setPassword(encoder.encode(personne.getPassword()));

        // Ajouter un rôle par défaut (par exemple, ROLE_ETUDIANT)
        Role defaultRole = roleRepository.findByName(ERole.ROLE_ETUDIANT)
                .orElseThrow(() -> new RuntimeException("role not found"));

        personne.getRoles().add(defaultRole);

        // Sauvegarder la personne avec le rôle associé
        repository.save(personne);

        return "User Added Successfully";
    }

    public String updateUser(Long id, Personne personneUpdate) {
        // Retrieve user from the repository using ID
        Optional<Personne> personne = repository.findById(id);

        if (personne.isPresent()) {
            Personne user = personne.get();

            // Update user properties
            user.setEmail(personneUpdate.getEmail());
            user.setFirstName(personneUpdate.getFirstName());
            user.setLastName(personneUpdate.getLastName());
            user.setPassword(encoder.encode(personneUpdate.getPassword()));  // Encoding password before saving

            // Save the updated user back to the repository
            repository.save(user);
            return "User updated successfully"; // Return success message
        } else {
            return "User not found with id: " + id; // User not found with provided ID
        }
    }

    public Personne getUserProfile(String username) {
        // Rechercher l'utilisateur par son email (ici le 'username' est l'email)
        Personne personne = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Retourner l'utilisateur trouvé
        return personne;
    }


}