package org.example.service;

import org.example.dto.ExpenseDto;
import org.example.exception.ExpenseTrackerException;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    int addExpense(ExpenseDto expenseDto) throws ExpenseTrackerException;
    ExpenseDto getExpense(int id) throws ExpenseTrackerException;
    List<ExpenseDto> getAllExpenses() throws ExpenseTrackerException;
    void updateExpense(ExpenseDto expenseDto) throws ExpenseTrackerException;
    void deleteExpense(int id) throws ExpenseTrackerException;
    List<ExpenseDto> getExpensesByDateRange(LocalDate start, LocalDate end) throws ExpenseTrackerException;
    double getTotalByCategory(int categoryId) throws ExpenseTrackerException;
}