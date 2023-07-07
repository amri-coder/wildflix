package com.wildflix.wildflix.services;

import java.util.List;

import com.wildflix.wildflix.models.Video;
import org.springframework.security.core.Authentication;

public interface VideoService {
	
	Video createVideo(Video video);
	List<Video> getAllVideos(boolean loggedIn);
	Video getVideoById(Long id, boolean loggedIn);
	void deleteVideoById(Long id, boolean loggedIn);
	Video modifyVideoById(Long id, Video video, boolean loggedIn);

}
