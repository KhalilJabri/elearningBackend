package org.example.crtekup.service;

import lombok.Getter;
import org.example.crtekup.models.Personne;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserInfoDetails implements UserDetails {

    private String username;
    private String password;
    // Retourner l'ID
    @Getter
    private Long id;  // Ajouter un champ pour l'ID
    private GrantedAuthority authority;

    public UserInfoDetails(Personne personne) {
        this.username = personne.getEmail();
        this.password = personne.getPassword();
        this.id = personne.getId();  // Assurez-vous que 'Personne' a un champ 'id'

        if (personne.getRole() != null) {
            // On prend le rôle et on crée l'autorité
            this.authority = new SimpleGrantedAuthority("ROLE_" + personne.getRole());
        } else {
            // Si aucun rôle n'est défini, on attribue un rôle par défaut
            this.authority = new SimpleGrantedAuthority("ROLE_USER");
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retourner une collection contenant une seule autorité
        return Collections.singletonList(this.authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
