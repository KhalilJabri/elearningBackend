

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
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String contenu;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiantCommentaire;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;
}