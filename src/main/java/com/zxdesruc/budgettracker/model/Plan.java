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
    private String name; // ��������: "������", "������", "��������" � �.�.

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount; // ����� �������������

    @Enumerated(EnumType.STRING)
    @NotNull
    private PlanType type; // ���: FIXED ��� CREDIT

    @Column(name = "monthly")
    private boolean monthly; // ����������� �� ����������

    @Column(name = "paid_this_month")
    private boolean paidThisMonth; // �������� �� � ������� ������
}
