package com.zxdesruc.budgettracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name; // Название: "Аренда", "Кредит", "Подписка" и т.д.

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount; // Сумма обязательства

    @Enumerated(EnumType.STRING)
    @NotNull
    private PlanType type; // Тип: FIXED или CREDIT

    @Column(name = "monthly")
    private boolean monthly; // Повторяется ли ежемесячно

    @Column(name = "paid_this_month")
    private boolean paidThisMonth; // Оплачено ли в текущем месяце
}
