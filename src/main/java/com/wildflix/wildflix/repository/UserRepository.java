package com.wildflix.wildflix.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wildflix.wildflix.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User save(User user);
	Optional<User> findById(Long id);
	List<User> findAll();
	Optional<User> findByEmail(String email);

}
