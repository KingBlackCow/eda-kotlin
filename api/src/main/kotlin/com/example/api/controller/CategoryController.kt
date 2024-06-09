package com.example.api.controller

import com.example.api.service.CategoryService
import com.example.api.db.category.CategoryEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categories")
class CategoryController(
    private val categoryService: CategoryService,
) {
    @GetMapping("")
    fun getCategories(): ResponseEntity<List<CategoryEntity>> {
        val categoryEntities: List<CategoryEntity> = categoryService.getCategories()
        return ResponseEntity.ok().body(categoryEntities)
    }
}
