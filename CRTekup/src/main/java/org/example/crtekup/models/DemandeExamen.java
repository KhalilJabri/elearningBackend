package org.example.crtekup.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DemandeExamen")
@ToString
@EqualsAndHashCode
public class DemandeExamen {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String description;
    private String coursName;
    private EnumType type;
    private EnumStatus status;
    private LocalDate created_at;
    private LocalDate date_examen;
    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiantDemandeExamen;
}
