package org.example.crtekup.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
public class Enseignant extends Personne{
    private String matiere;

    @OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL)
    private List<DemandeCours> ListDemandeCours;
}
