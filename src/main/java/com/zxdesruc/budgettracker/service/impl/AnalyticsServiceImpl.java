package com.zxdesruc.budgettracker.service.impl;

import com.zxdesruc.budgettracker.dto.CategorySummaryDTO;
import com.zxdesruc.budgettracker.dto.DailyBalanceDTO;
import com.zxdesruc.budgettracker.model.Transaction;
import com.zxdesruc.budgettracker.model.TransactionType;
import com.zxdesruc.budgettracker.repository.TransactionRepository;
import com.zxdesruc.budgettracker.service.AnalyticsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final TransactionRepository transactionRepository;

    public AnalyticsServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public BigDecimal getTotalIncomeBetween(LocalDate start, LocalDate end) {
        return transactionRepository.findByTypeAndDateBetween(TransactionType.INCOME, start, end)
                .stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getTotalExpenseBetween(LocalDate start, LocalDate end) {
        return transactionRepository.findByTypeAndDateBetween(TransactionType.EXPENSE, start, end)
                .stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getBalanceBetween(LocalDate start, LocalDate end) {
        return getTotalIncomeBetween(start, end).subtract(getTotalExpenseBetween(start, end));
    }

    @Override
    public List<CategorySummaryDTO> getExpensesByCategory(LocalDate start, LocalDate end) {
        return summarizeByCategory(TransactionType.EXPENSE, start, end);
    }

    @Override
    public List<CategorySummaryDTO> getIncomeByCategory(LocalDate start, LocalDate end) {
        return summarizeByCategory(TransactionType.INCOME, start, end);
    }

    private List<CategorySummaryDTO> summarizeByCategory(TransactionType type, LocalDate start, LocalDate end) {
        return transactionRepository.findByTypeAndDateBetween(type, start, end)
                .stream()
                .collect(Collectors.groupingBy(t -> t.getCategory().getName(),
                        Collectors.mapping(Transaction::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))))
                .entrySet()
                .stream()
                .map(entry -> new CategorySummaryDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<DailyBalanceDTO> getDailyBalance(LocalDate start, LocalDate end) {
        List<Transaction> transactions = transactionRepository.findByDateBetween(start, end);

        Map<LocalDate, List<Transaction>> grouped = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getDate));

        List<DailyBalanceDTO> result = new ArrayList<>();

        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            List<Transaction> daily = grouped.getOrDefault(date, Collections.emptyList());

            BigDecimal income = daily.stream()
                    .filter(t -> t.getType() == TransactionType.INCOME)
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal expense = daily.stream()
                    .filter(t -> t.getType() == TransactionType.EXPENSE)
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            result.add(new DailyBalanceDTO(date, income, expense, income.subtract(expense)));
        }

        return result;
    }
}
