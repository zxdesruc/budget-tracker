package com.zxdesruc.budgettracker.mapper;

import com.zxdesruc.budgettracker.dto.BudgetRequest;
import com.zxdesruc.budgettracker.dto.BudgetResponse;
import com.zxdesruc.budgettracker.model.Budget;
import com.zxdesruc.budgettracker.model.Category;

public class BudgetMapper {

    public static Budget toEntity(BudgetRequest dto, Category category) {
        Budget budget = new Budget();
        budget.setAmount(dto.getAmount());
        budget.setPeriodStart(dto.getStartDate());
        budget.setPeriodEnd(dto.getEndDate());
        budget.setCategory(category);
        return budget;
    }

    public static BudgetResponse toDto(Budget budget) {
        BudgetResponse dto = new BudgetResponse();
        dto.setId(budget.getId());
        dto.setAmount(budget.getAmount());
        dto.setStartDate(budget.getPeriodStart());
        dto.setEndDate(budget.getPeriodEnd());
        if (budget.getCategory() != null) {
            dto.setCategoryId(budget.getCategory().getId());
            dto.setCategoryName(budget.getCategory().getName());
        }
        return dto;
    }
}
