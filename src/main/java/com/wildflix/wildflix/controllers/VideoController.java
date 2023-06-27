package com.wildflix.wildflix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wildflix.wildflix.models.Video;
import com.wildflix.wildflix.services.VideoService;

@RestController
public class VideoController {
	
	@Autowired
	VideoService videoService;
	
	@PostMapping("/videos")
	public ResponseEntity<Video> createVideo(@RequestBody Video video) {
		return 
				new ResponseEntity<>(videoService.createVideo(video),
						HttpStatus.CREATED);
	}
	@GetMapping("Videos")
	public ResponseEntity<List<Video>> getAllVideo(){
		return
				new ResponseEntity<>(videoService.getAllVideos(), 
						HttpStatus.OK);
	}
	@GetMapping("videos/{id}")
	public ResponseEntity<Video> getVideoById(@PathVariable Long id){
		Video result = videoService.getVideoById(id);
		if(result != null) {
			return new ResponseEntity<>(result,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
}
