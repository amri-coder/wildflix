package com.wildflix.wildflix.servicesImplem;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wildflix.wildflix.models.Video;
import com.wildflix.wildflix.repository.VideoRepository;
import com.wildflix.wildflix.services.VideoService;

@Service
@Transactional
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
/*
	@Override
	public List<Video> findByTitleContainingOrDescriptionContainingOrCategoriesContainingOrSectionsContaining(String searchTerm, String searchTerm1, String searchTerm2) {
		return null;
	}*/
}
