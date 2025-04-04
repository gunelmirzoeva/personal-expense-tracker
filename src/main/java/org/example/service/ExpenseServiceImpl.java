package org.example.service;

import org.example.dao.ExpenseDao;
import org.example.dao.ExpenseDaoImpl;
import org.example.dto.ExpenseDto;
import org.example.entity.Expense;
import org.example.exception.ExpenseTrackerException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseDao expenseDao = new ExpenseDaoImpl();

    @Override
    public int addExpense(ExpenseDto expenseDto) throws ExpenseTrackerException {
        if (expenseDto.getAmount() <= 0) {
            throw new ExpenseTrackerException("Amount must be positive");
        }
        Expense expense = new Expense();
        expense.setAmount(expenseDto.getAmount());
        expense.setExpenseDate(expenseDto.getExpenseDate());
        expense.setCategoryId(expenseDto.getCategoryId());
        expense.setDescription(expenseDto.getDescription());
        int id = expenseDao.save(expense);
        return id;
    }

    @Override
    public ExpenseDto getExpense(int id) throws ExpenseTrackerException {
        Expense expense = expenseDao.findById(id);
        if (expense == null) {
            throw new ExpenseTrackerException("Expense not found with ID " + id);
        }
        return new ExpenseDto(id, expense.getAmount(), expense.getExpenseDate(),
                expense.getCategoryId(), expense.getDescription());
    }

    @Override
    public List<ExpenseDto> getAllExpenses() throws ExpenseTrackerException {
        List<Expense> expenses = expenseDao.findAll();
        List<ExpenseDto> dtos = new ArrayList<>();
        for (Expense expense : expenses) {
            dtos.add(new ExpenseDto(expense.getId(), expense.getAmount(), expense.getExpenseDate(),
                    expense.getCategoryId(), expense.getDescription()));
        }
        return dtos;
    }

    @Override
    public void updateExpense(ExpenseDto expenseDto) throws ExpenseTrackerException {
        if (expenseDto.getAmount() <= 0) {
            throw new ExpenseTrackerException("Amount must be positive");
        }
        Expense expense = new Expense(expenseDto.getId(), expenseDto.getAmount(),
                expenseDto.getExpenseDate(), expenseDto.getCategoryId(),
                expenseDto.getDescription());
        expenseDao.update(expense);
    }

    @Override
    public void deleteExpense(int id) throws ExpenseTrackerException {
        expenseDao.delete(id);
    }

    @Override
    public List<ExpenseDto> getExpensesByDateRange(LocalDate start, LocalDate end) throws ExpenseTrackerException {
        List<Expense> expenses = expenseDao.findByDateRange(start, end);
        List<ExpenseDto> dtos = new ArrayList<>();
        for (Expense expense : expenses) {
            dtos.add(new ExpenseDto(expense.getId(), expense.getAmount(), expense.getExpenseDate(),
                    expense.getCategoryId(), expense.getDescription()));
        }
        return dtos;
    }

    @Override
    public double getTotalByCategory(int categoryId) throws ExpenseTrackerException {
        return expenseDao.getTotalByCategory(categoryId);
    }
}