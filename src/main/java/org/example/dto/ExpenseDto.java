package org.example.dto;

import java.time.LocalDate;

public class ExpenseDto {
    private final int id;
    private double amount;
    private LocalDate expenseDate;
    private int categoryId;
    private String description;

    public ExpenseDto(int id, double amount, LocalDate expenseDate, int categoryId, String description) {
        this.id = id;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.categoryId = categoryId;
        this.description = description;
    }


    public ExpenseDto(double amount, LocalDate expenseDate, int categoryId, String description) {
        this(0, amount, expenseDate, categoryId, description);
    }

    public int getId() { return id; }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}