package com.wildflix.wildflix.controllers;

import java.util.List;

import com.wildflix.wildflix.models.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wildflix.wildflix.models.User;
import com.wildflix.wildflix.services.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return 
				new ResponseEntity<>(userService.createUser(user),
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

	@PutMapping("/users/{id}")
	public ResponseEntity<?> modifyVideoById(@PathVariable Long id){
		User user = userService.getUserById(id);
		if(user !=null) {
			userService.modifyUserById(id, user);
			return ResponseEntity.ok().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}

}
