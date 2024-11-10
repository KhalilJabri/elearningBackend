package org.example.crtekup.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String contenu;
    private LocalDate date;
}
