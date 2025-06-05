package com.zxdesruc.budgettracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmailTransactionDTO {
    private LocalDate date;        // Дата операции (можно из письма или дата получения)
    private BigDecimal amount;     // Сумма операции
    private String type;           // Тип операции: "INCOME" или "EXPENSE"
    private String categoryName;   // Название категории (если из письма или по умолчанию)
    private String source;         // Источник транзакции, например "EMAIL"
    private String comment;        // Комментарий или текст письма

    // Геттеры и сеттеры

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
