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
	public List<Video> getAllVideos(boolean loggedIn){
		if(loggedIn) {
			return videoRepository.findAll();
		}
		else return videoRepository.findByIsPrivate(false);
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
	@Override
	public void deleteVideoById(Long id) {
		videoRepository.deleteById(id);
	}

	@Override
	public Video modifyVideoById(Long id, Video newVideo){

		Optional<Video> video = videoRepository.findById(id);
		if(video.isPresent()) {
			video.get().setTitle(newVideo.getTitle()) ;
			video.get().setDescription(newVideo.getDescription());
			video.get().setPrivate(newVideo.isPrivate());
			video.get().setReleaseDate(newVideo.getReleaseDate());
			return video.get();
		}else {
			return null;
		}
	}
}
