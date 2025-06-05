package com.zxdesruc.budgettracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmailTransactionDTO {
    private LocalDate date;        // ���� �������� (����� �� ������ ��� ���� ���������)
    private BigDecimal amount;     // ����� ��������
    private String type;           // ��� ��������: "INCOME" ��� "EXPENSE"
    private String categoryName;   // �������� ��������� (���� �� ������ ��� �� ���������)
    private String source;         // �������� ����������, �������� "EMAIL"
    private String comment;        // ����������� ��� ����� ������

    // ������� � �������

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
