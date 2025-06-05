package com.zxdesruc.budgettracker.dto;

import com.zxdesruc.budgettracker.model.SourceType;
import com.zxdesruc.budgettracker.model.TransactionType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO used to receive transaction data from the client (e.g. form or API call).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {

    @NotNull(message = "Date is required")
    private LocalDate date;

    @Positive(message = "Amount must be positive")
    private double amount;

    private Long categoryId;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    @NotNull(message = "Source type is required")
    private SourceType source;

    @Size(max = 255, message = "Comment must be under 255 characters")
    private String comment;
}
