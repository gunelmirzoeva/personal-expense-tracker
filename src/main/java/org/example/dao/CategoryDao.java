package org.example.dao;

import org.example.entity.Category;
import org.example.exception.ExpenseTrackerException;

public interface CategoryDao extends Dao<Category>{
    Category findByName(String name) throws ExpenseTrackerException;
}
