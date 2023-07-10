package com.wildflix.wildflix.services;

import java.util.List;
import java.util.Optional;

import com.wildflix.wildflix.enums.RoleName;
import com.wildflix.wildflix.exceptions.UserNotFound;
import com.wildflix.wildflix.exceptions.VideoNotFoundException;
import com.wildflix.wildflix.models.User;
import com.wildflix.wildflix.models.Video;

public interface UserService {

	User createUser(User user, RoleName role);
	List<User> getAllUsers(); 
	User getUserById(Long id);
	User modifyUserById(Long id, User user);
	void deleteUserById(Long id);

	Optional<User> getUserByEmail(String email);

	void addRoleToUser(String email, RoleName roleName);
	String login (String email, String password);

	// A vérifier avec billel pour les deux méthodes
	List<Video> addVideoToFavorite(String email, Long idVideo) throws VideoNotFoundException, UserNotFound;
	List<Video> removeVideoFromFavorite(String email, Long idVideo) throws UserNotFound, VideoNotFoundException;

	boolean emailConfirmation(String email, int code) throws UserNotFound;
	boolean resetPasswordRequest(String email) throws UserNotFound;
}
