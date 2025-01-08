package org.example.crtekup.Controller;

import org.example.crtekup.models.AuthRequest;
import org.example.crtekup.models.AuthResponse;
import org.example.crtekup.models.Personne;
import org.example.crtekup.service.JwtService;
import org.example.crtekup.service.UserInfoDetails;
import org.example.crtekup.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Personne personne) {
        try {
            // Vérifier si un utilisateur avec le même email existe déjà
            if (service.isEmailExist(personne.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists!");
            }

            // Si l'email n'existe pas, ajouter l'utilisateur
            service.addUser(personne);
            return ResponseEntity.ok("User added successfully");

        } catch (Exception e) {
            // Gestion des autres erreurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while registering the user.");
        }
    }


    @PostMapping("/login")
    public AuthResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        if (authRequest == null) {
            throw new IllegalArgumentException("Request body is missing");
        }

        System.out.println("Request received for token generation: " + authRequest.getUsername());

        // Authentification
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        if (authentication.isAuthenticated()) {
            // Génération du token JWT
            String token = jwtService.generateToken(authRequest.getUsername());

            // Récupération des détails utilisateur
            UserInfoDetails userDetails = (UserInfoDetails) authentication.getPrincipal();

            // Récupérer les informations de l'utilisateur
            String username = userDetails.getUsername();
            Long userId = userDetails.getId();  // Récupérer l'ID

            // Extraire le rôle sans "ROLE_"
            String role = userDetails.getAuthorities().stream()
                    .map(authority -> authority.getAuthority().replace("ROLE_", ""))  // Enlever "ROLE_"
                    .findFirst()
                    .orElse("UNKNOWN");  // Valeur par défaut si aucun rôle n'est trouvé

            // Retourner le token, le username, l'id et le rôle
            return new AuthResponse(token, username, userId, role);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }


    @PatchMapping("/updateUser/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody Personne updatedPersonne) {
        try {
            // Appeler le service pour mettre à jour l'utilisateur
            String updateMessage = service.updateUser(id, updatedPersonne);
            return ResponseEntity.ok(updateMessage);
        } catch (Exception e) {
            // Gérer l'exception si l'utilisateur n'est pas trouvé ou s'il y a une erreur
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @GetMapping("/viewUsers")
    public ResponseEntity<List<Personne>> getUsers(Authentication authentication) {
        // Récupérer l'email de l'utilisateur connecté depuis Authentication
        String currentUserEmail = authentication.getName();

        // Afficher l'email de l'utilisateur connecté pour le débogage
        System.out.println("Utilisateur connecté : " + currentUserEmail);

        // Récupérer tous les utilisateurs
        List<Personne> personnes = service.getAllPersonne();

        // Filtrer l'utilisateur connecté de la liste (insensible à la casse)
        List<Personne> filteredPersonnes = personnes.stream()
                .filter(personne -> !personne.getEmail().equalsIgnoreCase(currentUserEmail))
                .collect(Collectors.toList());

        // Vérifier si la liste filtrée est vide
        if (filteredPersonnes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Retourner la liste filtrée
        return ResponseEntity.ok(filteredPersonnes);
    }


    @GetMapping("/viewProfil/{id}")
    public ResponseEntity<Personne> getViewProfil(@PathVariable Long id) {
        Personne personne = service.getPersonneById(id);
        if (personne != null) {
            return new ResponseEntity<>(personne, HttpStatus.OK);  // 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 404 Not Found
        }
    }
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            // Appeler le service pour supprimer l'utilisateur
            boolean isDeleted = service.deleteUser(id);

            if (isDeleted) {
                return ResponseEntity.ok("User deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            // Gestion des erreurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the user");
        }
    }

}
