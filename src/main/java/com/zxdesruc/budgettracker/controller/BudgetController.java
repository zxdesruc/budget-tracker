package com.zxdesruc.budgettracker.controller;

import com.zxdesruc.budgettracker.dto.BudgetRequest;
import com.zxdesruc.budgettracker.dto.BudgetResponse;
import com.zxdesruc.budgettracker.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for managing budgets.
 */
@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ResponseEntity<BudgetResponse> createBudget(@Valid @RequestBody BudgetRequest budgetRequestDTO) {
        BudgetResponse createdBudget = budgetService.createBudget(budgetRequestDTO);
        return new ResponseEntity<>(createdBudget, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetResponse> getBudgetById(@PathVariable Long id) {
        BudgetResponse budget = budgetService.getBudgetById(id);
        return ResponseEntity.ok(budget);
    }

    @GetMapping
    public ResponseEntity<List<BudgetResponse>> getAllBudgets() {
        List<BudgetResponse> budgets = budgetService.getAllBudgets();
        return ResponseEntity.ok(budgets);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetResponse> updateBudget(@PathVariable Long id,
                                                          @Valid @RequestBody BudgetRequest budgetRequestDTO) {
        BudgetResponse updatedBudget = budgetService.updateBudget(id, budgetRequestDTO);
        return ResponseEntity.ok(updatedBudget);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<BudgetResponse>> getBudgetsByCategory(@PathVariable Long categoryId) {
        List<BudgetResponse> budgets = budgetService.getBudgetsByCategory(categoryId);
        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/category/{categoryId}/date")
    public ResponseEntity<List<BudgetResponse>> getBudgetsForDate(
            @PathVariable Long categoryId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<BudgetResponse> budgets = budgetService.getBudgetsForDate(categoryId, date);
        return ResponseEntity.ok(budgets);
    }
}
