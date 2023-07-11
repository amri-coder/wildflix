package com.wildflix.wildflix.repository;

import com.wildflix.wildflix.models.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {

    Section save(Section section);
    Optional<Section> findById(Long id);
    List<Section> findAll();
    void deleteById(Long id);
}
