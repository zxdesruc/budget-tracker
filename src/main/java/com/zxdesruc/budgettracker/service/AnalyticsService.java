package com.zxdesruc.budgettracker.service;

import com.zxdesruc.budgettracker.dto.CategorySummaryDTO;
import com.zxdesruc.budgettracker.dto.DailyBalanceDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface AnalyticsService {

    BigDecimal getTotalIncomeBetween(LocalDate start, LocalDate end);
    BigDecimal getTotalExpenseBetween(LocalDate start, LocalDate end);
    BigDecimal getBalanceBetween(LocalDate start, LocalDate end);

    List<CategorySummaryDTO> getExpensesByCategory(LocalDate start, LocalDate end);
    List<CategorySummaryDTO> getIncomeByCategory(LocalDate start, LocalDate end);

    List<DailyBalanceDTO> getDailyBalance(LocalDate start, LocalDate end);
}
