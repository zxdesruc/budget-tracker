package com.zxdesruc.budgettracker.service.impl;

import com.zxdesruc.budgettracker.dto.PlanDTO;
import com.zxdesruc.budgettracker.mapper.PlanMapper;
import com.zxdesruc.budgettracker.model.Plan;
import com.zxdesruc.budgettracker.model.PlanType;
import com.zxdesruc.budgettracker.repository.PlanRepository;
import com.zxdesruc.budgettracker.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;

    @Override
    public PlanDTO createPlan(PlanDTO planDTO) {
        Plan saved = planRepository.save(PlanMapper.toEntity(planDTO));
        return PlanMapper.toDto(saved);
    }

    @Override
    public PlanDTO updatePlan(Long id, PlanDTO planDTO) {
        Plan existing = planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));

        existing.setName(planDTO.getName());
        existing.setAmount(planDTO.getAmount());
        existing.setType(PlanType.valueOf(planDTO.getType().toUpperCase())); // исправлено
        existing.setMonthly(planDTO.isMonthly());
        existing.setPaidThisMonth(planDTO.isPaidThisMonth());

        return PlanMapper.toDto(planRepository.save(existing));
    }

    @Override
    public void deletePlan(Long id) {
        planRepository.deleteById(id);
    }

    @Override
    public PlanDTO getPlanById(Long id) {
        return planRepository.findById(id)
                .map(PlanMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));
    }

    @Override
    public List<PlanDTO> getAllPlans() {
        return planRepository.findAll().stream()
                .map(PlanMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlanDTO> getMonthlyPlans() {
        return planRepository.findByMonthlyTrue().stream()
                .map(PlanMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void markAsPaid(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));
        plan.setPaidThisMonth(true);
        planRepository.save(plan);
    }

    @Override
    public void resetAllPaymentsForNewMonth() {
        List<Plan> allPlans = planRepository.findAll();
        for (Plan plan : allPlans) {
            plan.setPaidThisMonth(false);
        }
        planRepository.saveAll(allPlans);
    }
}
