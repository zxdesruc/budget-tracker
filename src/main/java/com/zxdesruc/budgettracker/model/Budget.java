package com.zxdesruc.budgettracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity representing a budget assigned to a specific category and date period.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Budget amount for the selected category and period
    @NotNull
    @Min(0)
    private BigDecimal amount;

    // Date period this budget applies to (e.g., month start)
    @NotNull
    private LocalDate periodStart;

    @NotNull
    private LocalDate periodEnd;

    // Category to which this budget applies
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;
}
