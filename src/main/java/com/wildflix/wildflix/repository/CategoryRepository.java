package com.wildflix.wildflix.repository;

import com.wildflix.wildflix.enums.RoleName;
import com.wildflix.wildflix.models.Category;
import com.wildflix.wildflix.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository <Category, Long> {
    Category save(Category category);
    Optional<Category> findByName(String name);
}
