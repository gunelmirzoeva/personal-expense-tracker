package org.example.dao;

import org.example.config.DatabaseConfig;
import org.example.entity.Expense;
import org.example.exception.ExpenseTrackerException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDaoImpl implements ExpenseDao {


    @Override
    public List<Expense> findByDateRange(LocalDate start, LocalDate end) throws ExpenseTrackerException {
        String sql = "select * from expenses where expense_date between ? and ?";
        List<Expense> expenses = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(start));
            ps.setDate(2, java.sql.Date.valueOf(end));
            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Expense expense = new Expense();
                    expense.setAmount(rs.getDouble("amount"));
                    expense.setExpenseDate(rs.getDate("expense_date").toLocalDate());
                    expense.setCategoryId(rs.getInt("category_id"));
                    expense.setDescription(rs.getString("description"));
                    expenses.add(expense);
                }
            }
        } catch (SQLException e) {
            throw new ExpenseTrackerException("Error fetching expenses by date range");
        }
        return expenses;
    }

    @Override
    public double getTotalByCategory(int categoryId) throws ExpenseTrackerException {
        String sql = "SELECT SUM(amount) FROM expenses WHERE category_id = ?";
        try(Connection conn = DatabaseConfig.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return rs.getDouble(1);
                } else {
                    return 0.0;
                }
            }
        } catch (SQLException e) {
            throw new ExpenseTrackerException("Error fetching expenses by category ID " + categoryId);
        }
    }

    @Override
    public void saveBatch(List<Expense> expenses) throws ExpenseTrackerException {
        String sql = "INSERT INTO expenses (amount, expense_date, category_id, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (Expense expense : expenses) {
                ps.setDouble(1, expense.getAmount());
                ps.setDate(2, java.sql.Date.valueOf(expense.getExpenseDate()));
                ps.setInt(3, expense.getCategoryId());
                ps.setString(4, expense.getDescription());
                ps.addBatch();
            }

            ps.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            try (Connection conn = DatabaseConfig.getConnection()) {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                throw new ExpenseTrackerException("Rollback failed: " + rollbackEx.getMessage() + rollbackEx);
            }
            throw new ExpenseTrackerException("Failed to save batch expenses: " + e.getMessage() + e);
        }
    }

    @Override
    public int save(Expense expense) throws ExpenseTrackerException {
        String sql = "INSERT INTO expenses (amount, expense_date, category_id, description) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, expense.getAmount());
            ps.setDate(2, java.sql.Date.valueOf(expense.getExpenseDate()));
            ps.setInt(3, expense.getCategoryId());
            ps.setString(4, expense.getDescription());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
                throw new ExpenseTrackerException("Failed to retrieve generated ID");
            }
        } catch (SQLException e) {
            throw new ExpenseTrackerException("Failed to save expense: " + e.getMessage());
        }
    }

    @Override
    public Expense findById(int id) throws ExpenseTrackerException {
        String sql = "SELECT * FROM expenses WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    Expense expense = new Expense();
                    expense.setAmount(rs.getDouble("amount"));
                    expense.setExpenseDate(rs.getDate("expense_date").toLocalDate());
                    expense.setCategoryId(rs.getInt("category_id"));
                    expense.setDescription(rs.getString("description"));
                    return expense;
                } else {
                    throw new ExpenseTrackerException("Expense not found with id " + id);
                }
            }
        } catch (SQLException e) {
            throw new ExpenseTrackerException("Failed to find expense with id " + id);
        }
    }

    @Override
    public List<Expense> findAll() throws ExpenseTrackerException {
        String sql = "SELECT * FROM expenses";
        List<Expense> expenses = new ArrayList<>();
        try(Connection conn = DatabaseConfig.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                Expense expense = new Expense();
                expense.setAmount(rs.getDouble("amount"));
                expense.setExpenseDate(rs.getDate("expense_date").toLocalDate());
                expense.setCategoryId(rs.getInt("category_id"));
                expense.setDescription(rs.getString("description"));
                expenses.add(expense);
            }

        } catch (SQLException e) {
            throw new ExpenseTrackerException("Failed to find expenses");
        }
        return expenses;
    }

    @Override
    public void update(Expense expense) throws ExpenseTrackerException {
        String sql = "UPDATE expenses SET amount = ?, expense_date = ?, description = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, expense.getAmount());
            ps.setDate(2, java.sql.Date.valueOf(expense.getExpenseDate()));
            ps.setString(3, expense.getDescription());
            ps.setInt(4, expense.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new ExpenseTrackerException("No expense found with ID " + expense.getId());
            }

        } catch (SQLException e) {
            throw new ExpenseTrackerException("Failed to update expense with ID " + expense.getId());
        }
    }

    @Override
    public void delete(int id) throws ExpenseTrackerException {
        String sql = "DELETE FROM expenses WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new ExpenseTrackerException("No expense found with ID " + id);
            }

        } catch (SQLException e) {
            throw new ExpenseTrackerException("Failed to delete expense with ID " + id);
        }
    }


}
