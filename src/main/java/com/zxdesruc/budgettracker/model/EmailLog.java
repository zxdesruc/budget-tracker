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

    // UID ������ � �������� ����� (IMAP UID)
    @NotNull
    @Column(unique = true)
    private String uid;

    // ���� ������
    @NotNull
    private LocalDateTime date;

    // ������ ��������� ������ (SPARSED, SKIPPED � �.�.)
    @NotNull
    private String status;

    // ������� ��������/������, ���� ����
    private String reason;
}
