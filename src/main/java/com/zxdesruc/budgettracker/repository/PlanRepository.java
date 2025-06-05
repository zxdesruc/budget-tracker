package com.zxdesruc.budgettracker.repository;

import com.zxdesruc.budgettracker.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findByMonthlyTrue();
}
