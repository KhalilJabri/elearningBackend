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

import java.util.Collections;
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
                Collections.emptyList()
//                List.of(new SimpleGrantedAuthority(personne.getRoles().toString()))
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
<<<<<<< HEAD

=======
>>>>>>> ac499f880cee283fc65d0eb7583841c897ec211a
        if (personne.isPresent()) {
            Personne user = personne.get();
            user.setEmail(personneUpdate.getEmail());
            user.setFirstName(personneUpdate.getFirstName());
            user.setLastName(personneUpdate.getLastName());
            user.setPassword(encoder.encode(personneUpdate.getPassword()));
            repository.save(user);
            return "User updated Successfully";
        } else {
<<<<<<< HEAD
            throw new RuntimeException("User not found"); // Si l'utilisateur n'est pas trouvé
        }
    }

    public Personne getPersonneProfile(String username) {
        return repository.findByEmail(username) // Utiliser le champ email
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

    }
=======
            return "User not found with id: " + id;
        }
    }

>>>>>>> ac499f880cee283fc65d0eb7583841c897ec211a
}