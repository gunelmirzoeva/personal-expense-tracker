package org.example.service;

import org.example.dto.CategoryDto;
import org.example.exception.ExpenseTrackerException;

import java.util.List;

public interface CategoryService {
    int addCategory(CategoryDto category) throws ExpenseTrackerException;
    CategoryDto getCategory(int id) throws ExpenseTrackerException;
    List<CategoryDto> getAllCategories() throws ExpenseTrackerException;
    void updateCategory(CategoryDto category) throws ExpenseTrackerException;
    void deleteCategory(int id) throws ExpenseTrackerException;
}
