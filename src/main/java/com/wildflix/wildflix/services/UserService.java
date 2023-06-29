package com.wildflix.wildflix.services;

import java.util.List;

import com.wildflix.wildflix.models.User;

public interface UserService {

	User createUser(User user);
	List<User> getAllUsers(); 
	User getUserById(Long id);
	User modifyUserById(Long id, User user);
	void deleteUserById(Long id);
}
