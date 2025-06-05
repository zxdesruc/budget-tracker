package com.zxdesruc.budgettracker.service;

import com.zxdesruc.budgettracker.dto.PlanDTO;

import java.util.List;

public interface PlanService {
    PlanDTO createPlan(PlanDTO planDTO);
    PlanDTO updatePlan(Long id, PlanDTO planDTO);
    void deletePlan(Long id);
    PlanDTO getPlanById(Long id);
    List<PlanDTO> getAllPlans();
    List<PlanDTO> getMonthlyPlans();
    void markAsPaid(Long id);
    void resetAllPaymentsForNewMonth();
}
