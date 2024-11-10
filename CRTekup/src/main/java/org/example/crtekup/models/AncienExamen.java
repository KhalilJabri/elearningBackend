package org.example.crtekup.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AncienExamen {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String file;
    private String name;
    private String description;
    private boolean status;
}
