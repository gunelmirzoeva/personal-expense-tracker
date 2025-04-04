package org.example.controller;

import org.example.dto.ExpenseDto;
import org.example.exception.ExpenseTrackerException;
import org.example.service.ExpenseService;
import org.example.service.ExpenseServiceImpl;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ExpenseController {
    private final ExpenseService expenseService = new ExpenseServiceImpl();
    private final Scanner scanner = new Scanner(System.in);

    public void addExpense() {
        try {
            System.out.print("Enter amount: ");
            double amount = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter category ID: ");
            int categoryId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter description: ");
            String description = scanner.nextLine();
            ExpenseDto expenseDto = new ExpenseDto(amount, date, categoryId, description);
            int id = expenseService.addExpense(expenseDto);
            System.out.println("Expense added successfully with ID: " + id);
        } catch (ExpenseTrackerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewAllExpenses() {
        try {
            List<ExpenseDto> expenses = expenseService.getAllExpenses();
            if (expenses.isEmpty()) {
                System.out.println("No expenses found.");
            } else {
                for (ExpenseDto expense : expenses) {
                    System.out.printf("ID: %d, Amount: %.2f, Date: %s, Category ID: %d, Description: %s%n",
                            expense.getId(), expense.getAmount(), expense.getExpenseDate(),
                            expense.getCategoryId(), expense.getDescription());
                }
            }
        } catch (ExpenseTrackerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewSummary() {
        try {
            System.out.print("Enter category ID: ");
            int categoryId = Integer.parseInt(scanner.nextLine());
            double total = expenseService.getTotalByCategory(categoryId);
            System.out.printf("Total spent in category %d: %.2f%n", categoryId, total);
        } catch (ExpenseTrackerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}