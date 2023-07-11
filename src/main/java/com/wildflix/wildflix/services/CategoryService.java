package com.wildflix.wildflix.services;

import com.wildflix.wildflix.models.Category;
import com.wildflix.wildflix.models.User;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    void deleteCategoryById(Long id);

    Category modifyCategoryById(Long id, Category category);
}
