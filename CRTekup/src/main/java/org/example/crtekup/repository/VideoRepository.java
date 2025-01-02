
package org.example.crtekup.repository;


import org.example.crtekup.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {

}