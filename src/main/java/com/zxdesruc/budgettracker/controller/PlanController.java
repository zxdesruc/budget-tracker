package com.zxdesruc.budgettracker.controller;

import com.zxdesruc.budgettracker.dto.PlanDTO;
import com.zxdesruc.budgettracker.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public ResponseEntity<PlanDTO> create(@RequestBody PlanDTO planDTO) {
        return ResponseEntity.ok(planService.createPlan(planDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(planService.getPlanById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanDTO> update(@PathVariable Long id, @RequestBody PlanDTO planDTO) {
        return ResponseEntity.ok(planService.updatePlan(id, planDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PlanDTO>> getAll() {
        return ResponseEntity.ok(planService.getAllPlans());
    }

    @GetMapping("/monthly")
    public ResponseEntity<List<PlanDTO>> getMonthly() {
        return ResponseEntity.ok(planService.getMonthlyPlans());
    }

    @PostMapping("/{id}/mark-paid")
    public ResponseEntity<Void> markAsPaid(@PathVariable Long id) {
        planService.markAsPaid(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-payments")
    public ResponseEntity<Void> resetPayments() {
        planService.resetAllPaymentsForNewMonth();
        return ResponseEntity.ok().build();
    }
}
