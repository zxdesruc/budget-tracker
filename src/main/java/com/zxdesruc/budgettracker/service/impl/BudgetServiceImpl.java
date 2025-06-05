package com.zxdesruc.budgettracker.service.impl;

import com.zxdesruc.budgettracker.dto.BudgetRequest;
import com.zxdesruc.budgettracker.dto.BudgetResponse;
import com.zxdesruc.budgettracker.mapper.BudgetMapper;
import com.zxdesruc.budgettracker.model.Budget;
import com.zxdesruc.budgettracker.model.Category;
import com.zxdesruc.budgettracker.repository.BudgetRepository;
import com.zxdesruc.budgettracker.repository.CategoryRepository;
import com.zxdesruc.budgettracker.service.BudgetService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository, CategoryRepository categoryRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public BudgetResponse createBudget(BudgetRequest budgetRequestDTO) {
        Category category = categoryRepository.findById(budgetRequestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + budgetRequestDTO.getCategoryId()));

        Budget budget = BudgetMapper.toEntity(budgetRequestDTO, category);
        Budget savedBudget = budgetRepository.save(budget);
        return BudgetMapper.toDto(savedBudget);
    }

    @Override
    public BudgetResponse getBudgetById(Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found with id: " + id));
        return BudgetMapper.toDto(budget);
    }

    @Override
    public List<BudgetResponse> getAllBudgets() {
        return budgetRepository.findAll().stream()
                .map(BudgetMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BudgetResponse> getBudgetsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));

        return budgetRepository.findByCategory(category).stream()
                .map(BudgetMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BudgetResponse> getBudgetsForDate(Long categoryId, LocalDate date) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));

        return budgetRepository.findByCategoryAndPeriodStartLessThanEqualAndPeriodEndGreaterThanEqual(category, date, date).stream()
                .map(BudgetMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BudgetResponse updateBudget(Long id, BudgetRequest budgetRequestDTO) {
        Budget existingBudget = budgetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found with id: " + id));

        Category category = categoryRepository.findById(budgetRequestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + budgetRequestDTO.getCategoryId()));

        existingBudget.setAmount(budgetRequestDTO.getAmount());
        existingBudget.setPeriodStart(budgetRequestDTO.getStartDate());
        existingBudget.setPeriodEnd(budgetRequestDTO.getEndDate());
        existingBudget.setCategory(category);

        Budget updatedBudget = budgetRepository.save(existingBudget);
        return BudgetMapper.toDto(updatedBudget);
    }

    @Override
    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }
}
