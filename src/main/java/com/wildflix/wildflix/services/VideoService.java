package com.wildflix.wildflix.services;

import java.util.List;

import com.wildflix.wildflix.models.Video;

public interface VideoService {
	
	Video createVideo(Video video);
	List<Video> getAllVideos(); 
	Video getVideoById(Long id);

}
