package com.wildflix.wildflix.servicesImplem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.wildflix.wildflix.models.Category;
import com.wildflix.wildflix.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.wildflix.wildflix.models.Video;
import com.wildflix.wildflix.repository.VideoRepository;
import com.wildflix.wildflix.services.VideoService;

@Service
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

	/* @Override
	public List<Video> getVideosByCategory(Category category){
		return videoRepository.findByCategory(category);
	} */
}
