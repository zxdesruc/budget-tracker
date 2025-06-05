package com.zxdesruc.budgettracker.repository;

import com.zxdesruc.budgettracker.model.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {

    Optional<EmailLog> findByUid(String uid);
}
