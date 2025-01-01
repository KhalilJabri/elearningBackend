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
    public String register(@RequestBody Personne personne) {
        return service.addUser(personne);
    }

    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
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
<<<<<<< HEAD
    @GetMapping("/viewUser/{username}")
    public ResponseEntity<Personne> getUserProfile(@PathVariable String username) {
        Personne personne = service.getPersonneProfile(username);

        if (personne != null) {
            return new ResponseEntity<>(personne, HttpStatus.OK);  // 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 404 Not Found
        }
    }
=======
>>>>>>> ac499f880cee283fc65d0eb7583841c897ec211a
    }
