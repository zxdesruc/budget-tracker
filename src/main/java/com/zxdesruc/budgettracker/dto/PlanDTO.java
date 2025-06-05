package com.zxdesruc.budgettracker.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanDTO {
    private Long id;
    private String name;
    private BigDecimal amount;
    private String type; // "FIXED" / "CREDIT"
    private boolean monthly;
    private boolean paidThisMonth;
}
