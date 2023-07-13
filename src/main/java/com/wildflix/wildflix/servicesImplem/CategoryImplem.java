package com.wildflix.wildflix.servicesImplem;

import com.wildflix.wildflix.models.Category;
import com.wildflix.wildflix.models.Video;
import com.wildflix.wildflix.repository.CategoryRepository;
import com.wildflix.wildflix.repository.VideoRepository;
import com.wildflix.wildflix.services.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryImplem implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }
    @Override
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
    @Override
    public Category getCategoryById(Long id){
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()) {
            return category.get();
        }else {
            return null;
        }
    }

    @Override
    public Category getCategoryByName(String name){
        Optional<Category> category = categoryRepository.findByName(name);
        if(category.isPresent()){
            return category.get();
        }
        return null;
    }
    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category modifyCategoryById(Long id, Category newCategory){

        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()) {
            Category c = category.get();
            c.setName(newCategory.getName());
            return categoryRepository.save(c);
        }else {
            return null;
        }
    }
}
