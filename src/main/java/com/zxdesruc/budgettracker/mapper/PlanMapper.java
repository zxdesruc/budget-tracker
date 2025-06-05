package com.zxdesruc.budgettracker.mapper;

import com.zxdesruc.budgettracker.dto.PlanDTO;
import com.zxdesruc.budgettracker.model.Plan;
import com.zxdesruc.budgettracker.model.PlanType;

public class PlanMapper {

    public static PlanDTO toDto(Plan plan) {
        return PlanDTO.builder()
                .id(plan.getId())
                .name(plan.getName())
                .amount(plan.getAmount())
                .type(plan.getType().name())
                .monthly(plan.isMonthly())
                .paidThisMonth(plan.isPaidThisMonth())
                .build();
    }

    public static Plan toEntity(PlanDTO dto) {
        return Plan.builder()
                .id(dto.getId())
                .name(dto.getName())
                .amount(dto.getAmount())
                .type(PlanType.valueOf(dto.getType().toUpperCase()))
                .monthly(dto.isMonthly())
                .paidThisMonth(dto.isPaidThisMonth())
                .build();
    }
}
