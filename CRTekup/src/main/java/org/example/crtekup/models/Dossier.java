
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
public class Dossier {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String name;
    private String description;
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "cours_id", nullable = false)
    private Cours coursDossier;

    @OneToMany(mappedBy = "dossier", cascade = CascadeType.ALL)
    private List<Video> ListVideos;
}