package com.wildflix.wildflix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.wildflix.wildflix.models.Video;
import com.wildflix.wildflix.services.VideoService;

@RestController
public class VideoController {
	
	@Autowired
	VideoService videoService;
	
	/**
	 * Create Video
	 * @param video
	 * @return
	 */
	@PostMapping("/admin/videos")
	public ResponseEntity<Video> createVideo(@RequestBody Video video) {
		return 
				new ResponseEntity<>(videoService.createVideo(video),
						HttpStatus.CREATED);
	}
	/**
	 * Show All Videos
	 * @return
	 */
	@GetMapping("/videos")
	public ResponseEntity<List<Video>> getAllVideos(Authentication auth){
		return
				new ResponseEntity<>(videoService.getAllVideos(auth!=null),
						HttpStatus.OK);
	}
	/**
	 * Show Video By ID
	 * @param id
	 * @return
	 */
	@GetMapping("/videos/{id}")
	public ResponseEntity<Video> getVideoById(@PathVariable Long id, Authentication auth){
		Video result = videoService.getVideoById(id, auth!=null);
		if(result != null) {
			return new ResponseEntity<>(result,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	/**
	 * Delete Video By ID
	 * @param id
	 * @return
	 */
	@DeleteMapping("/admin/videos/{id}")
	public ResponseEntity<?> deleteVideoById(@PathVariable Long id, Authentication auth){
		Video video = videoService.getVideoById(id, auth!=null);
		if(video !=null) {
			videoService.deleteVideoById(id, auth!=null);
			return ResponseEntity.ok().build();
		}else {
			return ResponseEntity.notFound().build();
		}		
	}

	@PutMapping("/admin/videos/{id}")
	public ResponseEntity<?> modifyVideoById(@PathVariable Long id, Authentication auth){
		Video video = videoService.getVideoById(id, auth!=null);
		if(video !=null) {
			videoService.modifyVideoById(id, video, auth!=null);
			return ResponseEntity.ok().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}
}
