package org.example.crtekup.models;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
public class Enseignant extends Personne {

    @NotBlank
    @Size(min = 1, max = 100)
    private String matiere;

    // Initialisation de la liste pour éviter les valeurs nulles
    @OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL)
    private List<DemandeCours> demandeCoursList = new ArrayList<>();
}
