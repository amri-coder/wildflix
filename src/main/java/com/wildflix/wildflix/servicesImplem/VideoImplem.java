package com.wildflix.wildflix.servicesImplem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.wildflix.wildflix.models.Category;
import com.wildflix.wildflix.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.wildflix.wildflix.models.Video;
import com.wildflix.wildflix.repository.VideoRepository;
import com.wildflix.wildflix.services.VideoService;

@Service
@Transactional
public class VideoImplem implements VideoService{

	@Autowired
	VideoRepository videoRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Video createVideo(Video video) {
		List<Category> categories = new ArrayList<>();
		video.getCategories().forEach(
				category -> categories.add(categoryRepository.findById(category.getId()).orElse(null))
		);
		video.setCategories(categories);
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
	public Video getVideoById(Long id, boolean loggedIn){
		Optional<Video> video = videoRepository.findById(id);
		if(video.isPresent()) {
			if(video.get().isPrivate()){
				if(loggedIn){
					return video.get();
				}
				else return null;
				}
			else return video.get();
			}
		 return null;
	}

	@Override
	public List<Video> getVideosByIds(List<Long> ids, boolean loggedIn){
		List<Video> videos = new ArrayList<>();
		for (Long id : ids) {
			Optional<Video> video = videoRepository.findById(id);
			if (video.isPresent()) {
				if (video.get().isPrivate()) {
					if (loggedIn) {
						videos.add(video.get());
					}
				} else {
					videos.add(video.get());
				}
			}
		}
		return videos;
	}
	@Override
	public List<Video> getVideosByCategories(List<Long> ids) {
		List<Video> result = new ArrayList<>();
		for (Long id : ids) {
			result.addAll(videoRepository.findByCategoryId(id));
		}
		return result;
	}
	@Override
	public void deleteVideoById(Long id, boolean loggedIn) {
		videoRepository.deleteById(id);
	}

	@Override
	public Video modifyVideoById(Long id, Video newVideo, boolean loggedIn){

		Optional<Video> video = videoRepository.findById(id);
		if(video.isPresent() && loggedIn) {
			Video v = video.get();
			v.setTitle(newVideo.getTitle()) ;
			v.setDescription(newVideo.getDescription());
			v.setPrivate(newVideo.isPrivate());
			//v.setReleaseDate(newVideo.getReleaseDate());
			return videoRepository.save(video.get());
		}else {
			return null;
		}
	}

	@Override
	public void addCategoryToVideo(Long id, String name){
		Optional<Video> video = videoRepository.findById(id);
		Optional<Category> category = categoryRepository.findByName(name);
		 if(video.isPresent() && category.isPresent()){
			 video.get().getCategories().add(category.get());
		 }
	}

	@Override
	public List<Video> getVideoByCategory(Long id){
		return videoRepository.findByCategoryId(id);
	}



/*
	@Override
	public List<Video> findByTitleContainingOrDescriptionContainingOrCategoriesContainingOrSectionsContaining(String searchTerm, String searchTerm1, String searchTerm2) {
		return null;
	}*/
}
