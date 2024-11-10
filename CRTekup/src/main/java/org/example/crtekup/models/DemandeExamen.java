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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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

}
