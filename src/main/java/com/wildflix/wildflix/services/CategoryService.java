package com.wildflix.wildflix.services;

import com.wildflix.wildflix.models.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    void deleteCategoryById(Long id);

    Category modifyCategoryById(Long id, Category category);
}
