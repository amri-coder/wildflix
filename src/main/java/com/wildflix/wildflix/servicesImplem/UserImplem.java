package com.wildflix.wildflix.servicesImplem;

import java.util.*;

import com.wildflix.wildflix.enums.RoleName;
import com.wildflix.wildflix.exceptions.JWTException;
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
		Random random = new Random();
		int code = random.nextInt(1000, 10000);
		user.setVerificationEmailCode(code);
		emailService.sendEmail(
				user.getEmail(),
				"Bienvenue à notre site wildflix",
				"Bienvenue sur notre plateforme de films !\n\nVoici le code de vérification pour s'inscrire sur notre site : " + code + "\n\n" +
						"Explorez notre vaste catalogue de films de tous les genres, profitez d'une expérience cinématographique immersive et partagez votre passion avec notre communauté.\n\n" +
						"Regardez vos films préférés à tout moment, sur tous vos appareils.\n\n" +
						"Notre équipe est là pour vous aider et assurer votre satisfaction.\n\n" +
						"Rejoignez-nous et vivez des moments captivants dans notre univers cinématographique.\n\n" +
						"Bienvenue dans notre monde du cinéma, votre destination pour un divertissement inoubliable !"
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
	public String login (String email, String password) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			if (user.get().isEmailVerified()) {

				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(
								email,
								password
						)
				);
				return jwtService.generateToken(user.get());
			}
			else { return "email not verified"; }
		} else {
			return "user not found";
		}
	}

	@Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
	public User modifyUserById(Long id, User newUser){

		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			User u = user.get();
			u.setFirstname(newUser.getFirstname());
			u.setLastname(newUser.getLastname());
			return userRepository.save(u);
		}else {
			return null;
		}
	}

	@Override
	public boolean emailConfirmation(String email, int code) throws UserNotFound {
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isPresent()){
			if(user.get().getVerificationEmailCode()==code){
				user.get().setEmailVerified(true);
				return true;
			}
			else
				return false;
		}
		else{
			throw new UserNotFound();
		}
	}

	@Override
	public boolean resetPasswordRequest(String email) throws UserNotFound {
		User user = userRepository.findByEmail(email).orElseThrow(
				()->new UserNotFound()
		);
		String jwt = jwtService.generateToken(user);
		StringBuilder sb = new StringBuilder();
		sb.append("Hello Dear, \n");
		sb.append("To reset your password, please click on the following link : \n");
		sb.append("http://localhost:4200/reset-password/"+jwt);
		sb.append("\n \n");
		sb.append("Cordially, \n");
		sb.append("WildFlix, The Best Movie In World ! \n");
		emailService.sendEmail(
				email,
				"Password reset",
				sb.toString()
		);
		return true;
	}

	@Override
	public void resetPassword(String token, String password) throws UserNotFound, JWTException {

		try{
			String userEmail = jwtService.extractUsername(token);
			User user = userRepository.findByEmail(userEmail).orElseThrow(
					()-> new UserNotFound()
			);
			String passwordEncoder=this.passwordEncoder.encode(password);
			user.setPassword(passwordEncoder);
		}catch(Exception e){
			throw new JWTException();
		}
	}
}
