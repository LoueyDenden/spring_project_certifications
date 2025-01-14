package org.springboot.certificationsservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springboot.certificationsservice.category.CategoryApp;
import org.springboot.certificationsservice.category.CategoryRequest;
import org.springboot.certificationsservice.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category/admin")
public class AdminCategoryController {
    private final CategoryService service;

    //CRUD CATEGORY
    @PostMapping
    public ResponseEntity<Integer> createCategory(
            @RequestBody @Valid CategoryRequest request
    ) {
        return ResponseEntity.ok(service.createCategory(request));
    }
    @PutMapping("/{category-id}")
    public ResponseEntity<Integer> updateCategory(
            @PathVariable("category-id") Integer id, @RequestBody @Valid CategoryRequest request
    ){
        return ResponseEntity.ok(service.updateCategory(request,id));
    }

    @DeleteMapping("/{category-id}")
    public ResponseEntity<Integer> deleteCategory(
            @PathVariable("category-id") Integer id
    ){
        return ResponseEntity.ok(service.deleteCategory(id));
    }

    @GetMapping("/{category-id}")
    public ResponseEntity<CategoryApp> findById(
            @PathVariable("category-id") Integer categoryId
    ) {
        return ResponseEntity.ok(service.findById(categoryId));
    }

    @GetMapping
    public ResponseEntity<List<CategoryApp>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

}
