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
	public ResponseEntity<User> getVideoById(@PathVariable Long id){
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

}
