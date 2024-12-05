package org.example.crtekup.models;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
public class Etudiant extends Personne {

    @NotNull
    private Date datebirth;

    @NotBlank
    @Size(min = 1, max = 50)
    private String niveau;

    @OneToMany(mappedBy = "etudiantDemandeExamen", cascade = CascadeType.ALL)
    private List<DemandeExamen> demandeExamenList = new ArrayList<>();

    @OneToMany(mappedBy = "etudiantCommentaire", cascade = CascadeType.ALL)
    private List<Commentaire> commentaireList = new ArrayList<>();

    @OneToMany(mappedBy = "etudiantFavoris", cascade = CascadeType.ALL)
    private List<Favoris> favorisList = new ArrayList<>();
}