package com.wildflix.wildflix.servicesImplem;

import java.util.*;

import com.wildflix.wildflix.enums.RoleName;
import com.wildflix.wildflix.exceptions.UserNotFound;
import com.wildflix.wildflix.exceptions.VideoNotFoundException;
import com.wildflix.wildflix.models.Role;
import com.wildflix.wildflix.models.Video;
import com.wildflix.wildflix.repository.RoleRepository;
import com.wildflix.wildflix.repository.VideoRepository;
import com.wildflix.wildflix.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.wildflix.wildflix.models.User;
import com.wildflix.wildflix.repository.UserRepository;
import com.wildflix.wildflix.services.UserService;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
@Transactional
@RequiredArgsConstructor
public class UserImplem implements UserService{
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	VideoRepository videoRepository;


	@Autowired
	JwtService jwtService;

	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	
	@Override
	public User createUser(User user, RoleName role) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User result = userRepository.save(user);
		addRoleToUser(result.getEmail(), role);
		emailService.sendEmail(
				"abbesbilel88@gmail.com",
				"Test sending email",
				"test body"
		);
		return result;
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
	public void addRoleToUser(String email, RoleName roleName) {
		Optional<Role> role = roleRepository.findByName(roleName);
		Optional<User> user = userRepository.findByEmail(email);

		if(role.isPresent() && user.isPresent()){
			user.get().getRoles().add(role.get());
		}
	}


	// A verifier Avec Billel
	@Override
	public List<Video> addVideoToFavorite(String email, Long videoId) throws VideoNotFoundException, UserNotFound {
		Optional<User> user = userRepository.findByEmail(email);
		Optional<Video> video = videoRepository.findById(videoId);
		if(user.isEmpty())
		{
			// user not found
			throw new UserNotFound();

		}
		else
			if(video.isEmpty())
			{
				//video not found
				throw new VideoNotFoundException();

			}
			else
			{
				user.get().getFavorite().add(video.get());
				return user.get().getFavorite();
			}
	}

	// A vérifier avec billel
	@Override
	public List<Video> removeVideoFromFavorite(String email, Long videoId) throws UserNotFound, VideoNotFoundException {
		Optional<User> user = userRepository.findByEmail(email);
		Optional<Video> video = videoRepository.findById(videoId);
		if(user.isEmpty()){
			throw new UserNotFound();
		}
		else{
			if(video.isEmpty()){
				throw new VideoNotFoundException();
			}
			else
			{
				user.get().getFavorite().remove(video.get());
				return user.get().getFavorite();
			}
		}
	}

	@Override
	public String login (String email, String password){
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()){
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							email,
							password
					)
			);
			return jwtService.generateToken(user.get());
		}
		return "user not found";
	}

	@Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
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
