package org.example.crtekup.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.example.crtekup.models.*;
import org.example.crtekup.repository.AdminRepository;
import org.example.crtekup.repository.EnseignantRepository;
import org.example.crtekup.repository.EtudiantRepository;
import org.example.crtekup.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

  /*  @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private EtudiantRepository etudiantRepository; */

    @Autowired
    @Lazy
    private PasswordEncoder encoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Personne personne = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Retourner une instance de UserInfoDetails plutôt que User standard
        return new UserInfoDetails(personne);
    }

    public String addUser(Personne personne) {
        //Test if user exist
        if (repository.findByEmail(personne.getEmail()).isPresent()) throw new RuntimeException("User already exists!");
        // Encode password before saving the user
        personne.setPassword(encoder.encode(personne.getPassword()));

        /* TODO */
        // enregistrement des users sera dans la table personne, alors qu'il existe des relations
        // avec la table Etudiant, Admin et Enseignant
        // il faut les ajouter dans la bonne classe
        /*
        if(ERole.ROLE_ETUDIANT.equals(personne.getRole())){
            adminRepository.save((Admin) personne);
        }else if(ERole.ROLE_ADMIN.equals(personne.getRole())){
            enseignantRepository.save((Enseignant) personne);
        }else if(ERole.ROLE_ENSEIGNANT.equals(personne.getRole())){
            etudiantRepository.save((Etudiant) personne);
        }
         */
        repository.save(personne);
        return "User Added Successfully";
    }

    public String updateUser(Long id, Personne personneUpdate) {
        Optional<Personne> personneOpt = repository.findById(id);
        if (personneOpt.isPresent()) {
            Personne user = personneOpt.get();

            // Mettre à jour partiellement les champs de l'utilisateur si ceux-ci sont non nuls
            if (personneUpdate.getEmail() != null) {
                user.setEmail(personneUpdate.getEmail());
            }
            if (personneUpdate.getFirstName() != null) {
                user.setFirstName(personneUpdate.getFirstName());
            }
            if (personneUpdate.getLastName() != null) {
                user.setLastName(personneUpdate.getLastName());
            }
            if (personneUpdate.getPassword() != null) {
                user.setPassword(encoder.encode(personneUpdate.getPassword()));  // Assurez-vous d'encoder le mot de passe
            }

            // Sauvegarder les modifications
            repository.save(user);
            return "User updated Successfully";
        } else {
            throw new RuntimeException("User not found"); // Lancer une exception si l'utilisateur n'est pas trouvé
        }
    }


    public Personne getPersonneProfile(String username) {
        return repository.findByEmail(username) // Utiliser le champ email
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

    }

    public List<Personne> getAllPersonne() {
        return repository.findAll();
    }

    public Personne getPersonneById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

    public Optional<Personne>findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public boolean isEmailExist(String email) {
        // Utilisation de findByEmail pour vérifier si l'email existe
        Optional<Personne> existingPersonne = repository.findByEmail(email);
        return existingPersonne.isPresent();  // Retourne true si l'email existe
    }
    public boolean deleteUser(Long id) {
        Optional<Personne> personne = repository.findById(id);
        if (personne.isPresent()) {
            repository.delete(personne.get());
            return true;
        } else {
            return false;
        }
    }

}