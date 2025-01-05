package org.example.crtekup.Controller;

import org.example.crtekup.models.AuthRequest;
import org.example.crtekup.models.Personne;
import org.example.crtekup.service.JwtService;
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
    public ResponseEntity<String>  register(@RequestBody Personne personne){
        try {
        service.addUser(personne);
        return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User already exists!");
        }
    }

    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        if (authRequest == null) {
            throw new IllegalArgumentException("Request body is missing");
        }
        System.out.println("Request received for token generation: " + authRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            System.out.println("User authenticated: " + authRequest.getUsername());
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody Personne updatedPersonne) {
        try {
            // Appeler le service pour mettre à jour l'utilisateur
            service.updateUser(id, updatedPersonne);
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            // Gérer l'exception si l'utilisateur n'est pas trouvé ou s'il y a une erreur
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @GetMapping("/viewUsers")
    public ResponseEntity<List<Personne>> getUsers() {
        List<Personne> personnes = service.getAllPersonne();
        if (personnes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retourne 404 si la liste est vide
        }
        return new ResponseEntity<>(personnes, HttpStatus.OK); // Retourne la liste si elle n'est pas vide

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
}
