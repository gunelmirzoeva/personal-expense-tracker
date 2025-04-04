package org.example.main;

import org.example.controller.CategoryController;
import org.example.controller.ExpenseController;
import java.util.Scanner;

public class ExpenseTrackerApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CategoryController categoryController = new CategoryController();
    private static final ExpenseController expenseController = new ExpenseController();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nExpense Tracker");
            System.out.println("1. Add Category");
            System.out.println("2. View All Categories");
            System.out.println("3. Add Expense");
            System.out.println("4. View All Expenses");
            System.out.println("5. View Category Summary");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        categoryController.addCategory();
                        break;
                    case 2:
                        categoryController.viewAllCategories();
                        break;
                    case 3:
                        expenseController.addExpense();
                        break;
                    case 4:
                        expenseController.viewAllExpenses();
                        break;
                    case 5:
                        expenseController.viewSummary();
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}