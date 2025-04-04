package org.example.dao;

import org.example.config.DatabaseConfig;
import org.example.entity.Category;
import org.example.exception.ExpenseTrackerException;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CategoryDaoImpl implements CategoryDao {

    @Override
    public Category findByName(String name) throws ExpenseTrackerException {
        String sql = "SELECT * FROM categories WHERE name = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category();
                    category.setName(rs.getString("name"));
                    return category;
                } else {
                    throw new ExpenseTrackerException("Can't find category with name " + name);
                }
            }
        } catch (SQLException e) {
            throw new ExpenseTrackerException("Error finding category with name " + name);
        }
    }


    @Override
    public int save(Category category) throws ExpenseTrackerException {
        String sql = "INSERT INTO categories(name) VALUES(?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return rs.getInt("id");
                }

            }
            throw new ExpenseTrackerException("Failed to retrieve generated ID");
        } catch (SQLException e) {
            throw new ExpenseTrackerException("Failed to save category with name " + category.getName());
        }
    }


    @Override
    public Category findById(int id) throws ExpenseTrackerException {
        String sql = "SELECT * FROM categories WHERE id = ?";
        try(Connection conn = DatabaseConfig.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    Category category = new Category();
                    category.setName(rs.getString("name"));
                    return category;
                } else {
                    throw new ExpenseTrackerException("Can't find category with id " + id);
                }
            }
        } catch (SQLException e) {
            throw new ExpenseTrackerException("Error finding category with id " + id);
        }
    }

    @Override
    public List<Category> findAll() throws ExpenseTrackerException {
        String sql = "SELECT * FROM categories";
        List<Category> categories = new ArrayList<>();
        try(Connection conn = DatabaseConfig.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                Category category = new Category();
                category.setName(rs.getString("name"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new ExpenseTrackerException("Error finding categories");
        }
        return categories;
    }

    @Override
    public void update(Category category) throws ExpenseTrackerException {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setInt(2, category.getId());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new ExpenseTrackerException("No category found with ID " + category.getId());
            }
        } catch (SQLException e) {
            throw new ExpenseTrackerException("Failed to update category with ID " + category.getId());
        }
    }

    @Override
    public void delete(int id) throws ExpenseTrackerException {
        String sql = "DELETE FROM categories WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted == 0) {
                throw new ExpenseTrackerException("No category found with ID " + id);
            }
        } catch (SQLException e) {
            throw new ExpenseTrackerException("Failed to delete category with ID " + id);
        }
    }

}
