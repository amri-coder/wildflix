package com.wildflix.wildflix.repository;

import java.util.List;
import java.util.Optional;

import com.wildflix.wildflix.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wildflix.wildflix.models.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long>{
	
	Video save(Video video);
	Optional<Video> findById(Long id);
	List<Video> findByIsPrivate(boolean isPrivate);
	List<Video> findAll();
	//List<Video> findByCategory(Category category);
	void deleteById(Long id);


}
