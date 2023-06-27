package com.wildflix.wildflix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.wildflix.wildflix.repository.UserRepository;

@RestController
public class UserController {
	
	
	@Autowired
	UserRepository userRepository;

}
