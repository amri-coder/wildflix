package com.wildflix.wildflix.controllers;

import com.wildflix.wildflix.models.Category;
import com.wildflix.wildflix.models.Video;
import com.wildflix.wildflix.services.CategoryService;
//import com.wildflix.wildflix.services.RoleService;
import com.wildflix.wildflix.services.UserService;
import com.wildflix.wildflix.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    //@Autowired
    //RoleService roleService;

    /**
     * Create category
     * @param category
     * @return
     */

    @PostMapping("/admin/categories")

    public ResponseEntity<Category> createCategory(@RequestBody Category category) {

        return
                new ResponseEntity<>(categoryService.createCategory(category),
                        HttpStatus.CREATED);

    }
    /**
     * Show All categories
     * @return
     */
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        return
                new ResponseEntity<>(categoryService.getAllCategories(),
                        HttpStatus.OK);
    }
    /**
     * Show category By ID
     * @param id
     * @return
     */
    @GetMapping("/categories/id/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
        Category result = categoryService.getCategoryById(id);
        if(result != null) {
            return new ResponseEntity<>(result,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/categories/{name}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String name){
        Category result = categoryService.getCategoryByName(name);
        if(result!=null){
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Delete category By ID
     * @param id
     * @return
     */
    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id){
        Category category = categoryService.getCategoryById(id);
        if(category !=null) {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<?> modifyCategoryById(@PathVariable Long id, @RequestBody Category newCategory){
        Category category = categoryService.getCategoryById(id);
        if(category !=null) {
            categoryService.modifyCategoryById(id, newCategory);
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
