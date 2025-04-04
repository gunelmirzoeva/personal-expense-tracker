package org.example.dao;

import org.example.entity.Expense;
import org.example.exception.ExpenseTrackerException;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseDao extends Dao<Expense> {
    List<Expense> findByDateRange(LocalDate start, LocalDate end) throws ExpenseTrackerException;
    double getTotalByCategory(int categoryId) throws ExpenseTrackerException;
    void saveBatch(List<Expense> expenses) throws ExpenseTrackerException;
}
