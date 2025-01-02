package org.example.crtekup.service;

import org.example.crtekup.models.Video;
import org.example.crtekup.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoService{

    @Autowired
    private VideoRepository videoRepository;

    public List<Video> findAll(){
        return videoRepository.findAll();
    }
    public Optional<Video> findById(long id){
        return videoRepository.findById(id);
    }

    public Video saveVideo(Video video){return videoRepository.save(video);}

    public Video updateVideo(Video video){
        return videoRepository.saveAndFlush(video);
    }

    public void deleteVideo(long id){
        videoRepository.deleteById(id);
    }

}