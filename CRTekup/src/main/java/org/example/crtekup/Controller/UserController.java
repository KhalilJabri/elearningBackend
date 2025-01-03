
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
import org.springframework.security.core.context.SecurityContextHolder;
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
    @GetMapping("/profile")
    public ResponseEntity<Personne> getUserProfile(Authentication authentication) {
        String username = authentication.getName();  // Extract username from the token
        Personne userProfile = service.getUserProfile(username);  // Retrieve user profile
        return ResponseEntity.ok(userProfile);  // Return profile in response
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody Personne updatedPersonne) {
        String result = service.updateUser(id, updatedPersonne);
        if (result.startsWith("User not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }

}