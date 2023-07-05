package com.wildflix.wildflix.services;

import java.util.List;

import com.wildflix.wildflix.models.Video;

public interface VideoService {
	
	Video createVideo(Video video);
	List<Video> getAllVideos(); 
	Video getVideoById(Long id);
	void deleteVideoById(Long id);
	Video modifyVideoById(Long id, Video video);

	/*
	List<Video> findByTitleContainingOrDescriptionContainingOrCategoriesContainingOrSectionsContaining(String searchTerm, String searchTerm1, String searchTerm2);
	 */
}
