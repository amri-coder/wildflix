package com.wildflix.wildflix.controllers;

import java.util.ArrayList;

import java.util.Map;
import java.util.List;

import com.wildflix.wildflix.enums.RoleName;
import com.wildflix.wildflix.exceptions.UserNotFound;
import com.wildflix.wildflix.exceptions.VideoNotFoundException;
import com.wildflix.wildflix.models.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.wildflix.wildflix.models.User;
import com.wildflix.wildflix.services.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return 
				new ResponseEntity<>(userService.createUser(user, RoleName.USER),
						HttpStatus.CREATED);
	}
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id){
		User user = userService.getUserById(id);
		if(user != null) {
		return 
				new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);}
		else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers(){
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable Long id){
		User user = userService.getUserById(id);
		if(user !=null) {
			userService.deleteUserById(id);
			return ResponseEntity.ok().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/users")
	public ResponseEntity<?> modifyUserById(@PathVariable Long id, Authentication authentication){

		User user = userService.getUserById(id);
		if(user !=null) {
			userService.modifyUserById(id, user);
			return ResponseEntity.ok().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/users/getUserName")
	public String getName (Authentication authentication){
		return authentication.getName();
	}

	@GetMapping("/users/getRoles")
	public List<String> getRoles(Authentication authentication){
		List<String> roles = new ArrayList<>();
		for (GrantedAuthority grantedAuthority :authentication.getAuthorities()){
			roles.add(grantedAuthority.getAuthority());
		}
		return roles;
	}

	@GetMapping("/users/me")
	public ResponseEntity<User> getUserMe(Authentication auth){
		User user = userService.getUserById(((User)auth.getPrincipal()).getId());

		if(user != null) {
			return
					new ResponseEntity<>(userService.getUserById(((User)auth.getPrincipal()).getId()), HttpStatus.OK);} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/users/addVideoToFavorite")
	public ResponseEntity<List<Video>> addVideoToFavorite(@RequestBody Map<String,Long> request, Authentication authentication){
		try {
			return new ResponseEntity<>(userService.addVideoToFavorite(authentication.getName(), request.get("idVideo")), HttpStatus.OK);
		}
		catch(VideoNotFoundException videoNotFoundException){
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
		catch(UserNotFound userNotFound){
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	}
	// A vérifier avec billel
	@PostMapping("/users/removeVideoFromFavorite")
	public ResponseEntity<List<Video>> removeVideoFromFavorite(@RequestBody Map<String, Long> request, Authentication authentication){
		try {
			return new ResponseEntity<>(userService.removeVideoFromFavorite(authentication.getName(), request.get("idVideo")), HttpStatus.OK);
		}
		catch(VideoNotFoundException videoNotFoundException){
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
		catch(UserNotFound userNotFound){
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	}

}
