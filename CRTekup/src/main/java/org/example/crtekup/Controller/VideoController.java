package org.example.crtekup.Controller;

import org.example.crtekup.models.Dossier;
import org.example.crtekup.models.Video;
import org.example.crtekup.service.VideoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.beans.factory.annotation.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    // Le répertoire où les vidéos seront stockées
    @Value("${video.upload-dir}")
    private String uploadDir;

    // Upload pour la vidéo
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadVideo(@RequestParam("title") String title,
                                              @RequestParam("description") String description,
                                              @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Le fichier est vide", HttpStatus.BAD_REQUEST);
        }

        try {
            // Créez un répertoire pour stocker les vidéos si nécessaire
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            // Sauvegarder la vidéo sur le disque
            Path destinationPath = path.resolve(file.getOriginalFilename());
            file.transferTo(destinationPath);
            // Enregistrer le chemin de la vidéo dans la base de données
            Video video = new Video();
            video.setTitle(title);
            video.setDescription(description);
            video.setAvailable("True");
            video.setFilepath(destinationPath.toString());

            // Sauvegarder l'objet vidéo dans la base de données
            videoService.saveVideo(video);

            // Retourner une réponse indiquant que la vidéo a été téléchargée avec succès
            return new ResponseEntity<>("Vidéo téléchargée avec succès : " + file.getOriginalFilename(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Erreur lors du téléchargement de la vidéo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Voir une vidéo à partir du disque
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> viewVideo(@PathVariable Long id) {
        Optional<Video> videoOpt = videoService.findById(id);

        if (!videoOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si la vidéo n'existe pas
        }

        Video video = videoOpt.get();
        try {
            // Recherche de la vidéo sur le disque
            Path videoPath = Paths.get(video.getFilepath());
            if (!Files.exists(videoPath)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Lecture du fichier vidéo
            byte[] videoBytes = Files.readAllBytes(videoPath);

            // Retourner la vidéo avec le bon type MIME
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("video/mp4")); // Par défaut, mp4, ajustez si nécessaire
            headers.setContentLength(videoBytes.length);

            return new ResponseEntity<>(videoBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Video pour supprimer un cours
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> videoCours(@PathVariable Long id) {
        try {
            videoService.deleteVideo(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }
    // Récupérer tous les videos
    @GetMapping("/getAll")
    public List<Video> getAllVideos() {
        return videoService.getAllVideos();
    }
}
