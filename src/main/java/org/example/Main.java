package org.example;

import org.example.dao.ExpenseDaoImpl;
import org.example.entity.Expense;
import org.example.exception.ExpenseTrackerException;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ExpenseDaoImpl expenseDao = new ExpenseDaoImpl();

        try {
            // Test save() method
            Expense newExpense = new Expense();
            newExpense.setAmount(100.0);
            newExpense.setExpenseDate(LocalDate.now());
            newExpense.setCategoryId(1);
            newExpense.setDescription("Test expense");
            expenseDao.save(newExpense);
            System.out.println("Expense saved successfully!");

            // Test findById() method
            Expense foundExpense = expenseDao.findById(1); // Assuming 1 is a valid ID
            System.out.println("Found expense: " + foundExpense);

            // Test findByDateRange() method
            LocalDate startDate = LocalDate.of(2025, 4, 1);
            LocalDate endDate = LocalDate.of(2025, 4, 30);
            List<Expense> expensesInRange = expenseDao.findByDateRange(startDate, endDate);
            System.out.println("Expenses in range: " + expensesInRange);

        } catch (ExpenseTrackerException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
