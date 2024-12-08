package org.example.crtekup.service;

import org.example.crtekup.models.Personne;
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

    @Autowired
    @Lazy
    private PasswordEncoder encoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Personne personne = repository.findByEmail(username) // Utiliser le champ email
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        return new org.springframework.security.core.userdetails.User(
                personne.getEmail(), // Utiliser email pour l'authentification
                personne.getPassword(),
                List.of(new SimpleGrantedAuthority(personne.getRoles().toString()))
        );
    }


    public String addUser(Personne personne) {
        // Encode password before saving the user
        personne.setPassword(encoder.encode(personne.getPassword()));
        repository.save(personne);
        return "User Added Successfully";
    }

    public String updateUser(Long id, Personne personneUpdate) {//Mise à jour des informations de personne
        Optional<Personne> personne = repository.findById(id);
        if (personne.isPresent()) {
            Personne user = personne.get();
            user.setEmail(personneUpdate.getEmail());
            user.setFirstName(personneUpdate.getFirstName());
            user.setLastName(personneUpdate.getLastName());
            user.setPassword(encoder.encode(personneUpdate.getPassword()));
            repository.save(user);
            return "User updated Successfully";
        } else {
            return "User not found with id: " + id;
        }
    }

}