package com.wildflix.wildflix.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wildflix.wildflix.models.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long>{
	
	Video save(Video video);
	Optional<Video> findById(Long id);
	List<Video> findAll();
	void deleteById(Long id);
	/*
	List<Video> findByTitleContainingOrDescriptionContainingOrCategoriesContainingOrSectionsContaining(String searchTerm, String searchTerm1, String searchTerm2);
	*/

}
