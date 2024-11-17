package org.example.crtekup.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
public class Admin extends Personne{
    private LocalDate dateInscription;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<AncienExamen> ListAncienExamens;

    @OneToMany(mappedBy = "adminCours", cascade = CascadeType.ALL)
    private List<Cours> ListCours;
}
