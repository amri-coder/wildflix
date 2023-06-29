package com.wildflix.wildflix.servicesImplem;

import java.util.List;
import java.util.Optional;

import com.wildflix.wildflix.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wildflix.wildflix.models.User;
import com.wildflix.wildflix.repository.UserRepository;
import com.wildflix.wildflix.services.UserService;

@Service
public class UserImplem implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}
	@Override
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	@Override
	public User getUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			return user.get();
		}else {
		return null;}
	}
	@Override
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User modifyUserById(Long id, User newUser){

		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			user.get().setFirstname(newUser.getFirstname());
			user.get().setLastname(newUser.getLastname());
			user.get().setEmail(newUser.getEmail());
			user.get().setPassword(newUser.getPassword());
			user.get().setFavorite(newUser.getFavorite());
			return user.get();
		}else {
			return null;
		}
	}
}
