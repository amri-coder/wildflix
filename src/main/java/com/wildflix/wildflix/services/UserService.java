package com.wildflix.wildflix.services;

import java.util.List;
import java.util.Optional;

import com.wildflix.wildflix.enums.RoleName;
import com.wildflix.wildflix.models.User;

public interface UserService {

	User createUser(User user);
	List<User> getAllUsers(); 
	User getUserById(Long id);
	User modifyUserById(Long id, User user);
	void deleteUserById(Long id);

	Optional<User> getUserByEmail(String email);

	void addRoleToUser(String email, RoleName roleName);
	String login (String email, String password);
}
