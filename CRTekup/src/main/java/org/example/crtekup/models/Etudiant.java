package org.example.crtekup.models;

import jakarta.persistence.*;
import lombok.*;
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
public class Etudiant extends Personne{
    private Date datebirth;
    private String niveau;

    @OneToMany(mappedBy = "etudiantDemandeExamen", cascade = CascadeType.ALL)
    private List<DemandeExamen> ListDemandeExamen;

    @OneToMany(mappedBy = "etudiantCommentaire", cascade = CascadeType.ALL)
    private List<Commentaire> ListCommentaire;

    @OneToMany(mappedBy = "etudiantFavoris", cascade = CascadeType.ALL)
    private List<Favoris> favoris = new ArrayList<>();
}
