package com.example.api.service

import com.example.api.db.category.CategoryEntity
import com.example.api.db.category.CategoryJpaRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryJpaRepository: CategoryJpaRepository
) {
    fun getCategories(): List<CategoryEntity> {
        return categoryJpaRepository.findAll()
    }

    fun getCategoryNameByCategoryId(categoryId: Long): String {
        val categoryEntity: CategoryEntity = categoryJpaRepository.findById(categoryId).orElseThrow()
        return categoryEntity.name
    }
}