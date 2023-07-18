package com.wildflix.wildflix.services;

import java.util.List;

import com.wildflix.wildflix.models.Category;
import com.wildflix.wildflix.models.Video;
import org.springframework.security.core.Authentication;

public interface VideoService {
	
	Video createVideo(Video video);
	List<Video> getAllVideos(boolean loggedIn);
	Video getVideoById(Long id, boolean loggedIn);
	List<Video> getVideosByIds(List<Long> ids, boolean loggedIn);
	void deleteVideoById(Long id, boolean loggedIn);
	Video modifyVideoById(Long id, Video video, boolean loggedIn);
	void addCategoryToVideo(Long id, String name);

	List<Video> getVideoByCategory(Long id);
	List<Video> getVideosByCategories(List<Long> ids);

	/*
	List<Video> findByTitleContainingOrDescriptionContainingOrCategoriesContainingOrSectionsContaining(String searchTerm, String searchTerm1, String searchTerm2);
	 */
}
