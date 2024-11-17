package org.example.crtekup.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
public class DemandeCours {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String description;
    private String coursName;
    private EnumStatus status;
    private LocalDate created_at;
    @ManyToOne
    @JoinColumn(name = "enseignant_id", nullable = false)
    private Enseignant enseignant;

    @OneToOne
    @JoinColumn(name = "cours_id", nullable = false, unique = true)
    private Cours cours;
}
