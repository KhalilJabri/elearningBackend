package org.example.crtekup.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String title;
    private String description;
    private String available;
    private String filepath;

//    @ManyToOne
//    @JoinColumn(name = "dossier_id", nullable = false)
//    private Dossier dossier;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<Commentaire> commentaires = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Cours course;

}