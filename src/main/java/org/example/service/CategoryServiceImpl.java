package org.example.service;

import org.example.dao.CategoryDao;
import org.example.dao.CategoryDaoImpl;
import org.example.dto.CategoryDto;
import org.example.entity.Category;
import org.example.exception.ExpenseTrackerException;
import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public int addCategory(CategoryDto categoryDto) throws ExpenseTrackerException {
        Category category = new Category();
        category.setName(categoryDto.getName());
        int id = categoryDao.save(category);
        return id;
    }

    @Override
    public CategoryDto getCategory(int id) throws ExpenseTrackerException {
        Category category = categoryDao.findById(id);
        if (category == null) {
            throw new ExpenseTrackerException("Category not found with ID " + id);
        }
        return new CategoryDto(id, category.getName());
    }

    @Override
    public List<CategoryDto> getAllCategories() throws ExpenseTrackerException {
        List<Category> categories = categoryDao.findAll();
        List<CategoryDto> dtos = new ArrayList<>();
        for (Category category : categories) {
            dtos.add(new CategoryDto(category.getId(), category.getName()));
        }
        return dtos;
    }

    @Override
    public void updateCategory(CategoryDto categoryDto) throws ExpenseTrackerException {
        Category category = new Category(categoryDto.getId(), categoryDto.getName());
        categoryDao.update(category);
    }

    @Override
    public void deleteCategory(int id) throws ExpenseTrackerException {
        categoryDao.delete(id);
    }
}