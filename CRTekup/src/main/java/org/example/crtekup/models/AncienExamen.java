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
public class AncienExamen {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String file;
    private String name;
    private String description;
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;
}
