// dto/CategorySummaryDTO.java
package com.zxdesruc.budgettracker.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategorySummaryDTO {
    private String category;
    private BigDecimal total;
}
