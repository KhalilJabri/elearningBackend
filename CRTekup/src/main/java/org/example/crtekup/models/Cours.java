
package org.example.crtekup.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String name;
    private String description;
    private boolean available;
    private int rate;

    @OneToMany(mappedBy = "coursDossier", cascade = CascadeType.ALL)
    private List<Dossier> ListDossiers;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin adminCours;

    @OneToOne(mappedBy = "cours", cascade = CascadeType.ALL)
    private DemandeCours demandeCours;

    @OneToMany(mappedBy = "coursFavoris", cascade = CascadeType.ALL)
    private List<Favoris> favoris = new ArrayList<>();
}