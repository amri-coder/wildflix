package com.wildflix.wildflix.servicesImplem;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wildflix.wildflix.models.Video;
import com.wildflix.wildflix.repository.VideoRepository;
import com.wildflix.wildflix.services.VideoService;

@Service
public class VideoImplem implements VideoService{
	
	@Autowired
	VideoRepository videoRepository;
	
	@Override
	public Video createVideo(Video video) {
		return videoRepository.save(video);
	}
	@Override
	public List<Video> getAllVideos(){
		return videoRepository.findAll();
	}
	@Override
	public Video getVideoById(Long id){
		Optional<Video> video = videoRepository.findById(id);
		if(video.isPresent()) {
			return video.get();
		}else {
			return null;
			}
	}
}
