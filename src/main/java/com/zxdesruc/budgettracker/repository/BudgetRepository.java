package com.zxdesruc.budgettracker.repository;

import com.zxdesruc.budgettracker.model.Budget;
import com.zxdesruc.budgettracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByCategory(Category category);

    List<Budget> findByPeriodStartBetween(LocalDate start, LocalDate end);

    List<Budget> findByCategoryAndPeriodStartLessThanEqualAndPeriodEndGreaterThanEqual(
            Category category, LocalDate date1, LocalDate date2);
}
