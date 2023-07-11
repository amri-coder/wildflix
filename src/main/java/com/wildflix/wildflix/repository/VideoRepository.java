package com.wildflix.wildflix.repository;

import java.util.List;
import java.util.Optional;

import com.wildflix.wildflix.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wildflix.wildflix.models.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long>{
	
	Video save(Video video);
	Optional<Video> findById(Long id);
	List<Video> findByIsPrivate(boolean isPrivate);
	List<Video> findAll();

	@Query(nativeQuery = true,
			value = "SELECT v.* " +
					"FROM video v " +
					"INNER JOIN categories cs ON v.id = cs.video_id " +
					"INNER JOIN category c ON c.id = cs.category_id " +
					"WHERE c.id = :categoryId")
	List<Video> findByCategoryId(@Param("categoryId") Long id);
	void deleteById(Long id);
	/*
	List<Video> findByTitleContainingOrDescriptionContainingOrCategoriesContainingOrSectionsContaining(String searchTerm, String searchTerm1, String searchTerm2);
	*/

}
