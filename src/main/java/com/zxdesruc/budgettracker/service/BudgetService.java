package com.zxdesruc.budgettracker.service;

import com.zxdesruc.budgettracker.dto.BudgetRequest;
import com.zxdesruc.budgettracker.dto.BudgetResponse;

import java.time.LocalDate;
import java.util.List;

public interface BudgetService {

    BudgetResponse createBudget(BudgetRequest budgetRequestDTO);

    BudgetResponse getBudgetById(Long id);

    List<BudgetResponse> getAllBudgets();

    List<BudgetResponse> getBudgetsByCategory(Long categoryId);

    List<BudgetResponse> getBudgetsForDate(Long categoryId, LocalDate date);

    BudgetResponse updateBudget(Long id, BudgetRequest budgetRequestDTO);

    void deleteBudget(Long id);
}
