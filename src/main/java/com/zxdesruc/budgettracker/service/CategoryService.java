package com.zxdesruc.budgettracker.service;

import com.zxdesruc.budgettracker.dto.CategoryRequest;
import com.zxdesruc.budgettracker.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryRequest request);
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    void deleteCategory(Long id);
}
