package org.example.controller;

import org.example.dto.CategoryDto;
import org.example.exception.ExpenseTrackerException;
import org.example.service.CategoryService;
import org.example.service.CategoryServiceImpl;
import java.util.List;
import java.util.Scanner;

public class CategoryController {
    private final CategoryService categoryService = new CategoryServiceImpl();
    private final Scanner scanner = new Scanner(System.in);

    public void addCategory() {
        try {
            System.out.print("Enter category name: ");
            String name = scanner.nextLine();
            CategoryDto categoryDto = new CategoryDto(0, name);
            int id = categoryService.addCategory(categoryDto);
            System.out.println("Category added successfully with ID: " + id);
        } catch (ExpenseTrackerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewAllCategories() {
        try {
            List<CategoryDto> categories = categoryService.getAllCategories();
            if (categories.isEmpty()) {
                System.out.println("No categories found.");
            } else {
                for (CategoryDto category : categories) {
                    System.out.printf("ID: %d, Name: %s%n", category.getId(), category.getName());
                }
            }
        } catch (ExpenseTrackerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}