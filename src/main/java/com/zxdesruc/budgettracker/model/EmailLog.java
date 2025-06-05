package com.zxdesruc.budgettracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "email_logs")
public class EmailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // UID письма в почтовом ящике (IMAP UID)
    @NotNull
    @Column(unique = true)
    private String uid;

    // Дата письма
    @NotNull
    private LocalDateTime date;

    // Статус обработки письма (SPARSED, SKIPPED и т.п.)
    @NotNull
    private String status;

    // Причина пропуска/ошибки, если есть
    private String reason;
}
