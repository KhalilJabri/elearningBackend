package org.example.crtekup.models;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
public class Admin extends Personne {

    @NotNull
    private LocalDate dateInscription;

    // Initialisation des listes pour éviter les valeurs nulles
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<AncienExamen> ancienExamensList = new ArrayList<>();

    @OneToMany(mappedBy = "adminCours", cascade = CascadeType.ALL)
    private List<Cours> coursList = new ArrayList<>();
}