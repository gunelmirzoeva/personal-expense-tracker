package org.example.dao;

import org.example.exception.ExpenseTrackerException;

import java.util.List;

public interface Dao<T> {
    int save(T t) throws ExpenseTrackerException;
    T findById(int id) throws ExpenseTrackerException;
    List<T> findAll() throws ExpenseTrackerException;
    void update(T t) throws ExpenseTrackerException;
    void delete(int id) throws ExpenseTrackerException;
}
