package com.zxdesruc.budgettracker.service.impl;

import com.zxdesruc.budgettracker.dto.EmailTransactionDTO;
import com.zxdesruc.budgettracker.model.Category;
import com.zxdesruc.budgettracker.model.SourceType;
import com.zxdesruc.budgettracker.model.Transaction;
import com.zxdesruc.budgettracker.model.TransactionType;
import com.zxdesruc.budgettracker.repository.CategoryRepository;
import com.zxdesruc.budgettracker.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailTransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public EmailTransactionService(TransactionRepository transactionRepository,
                                   CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    public Transaction saveEmailTransaction(EmailTransactionDTO dto) {
        // Найти категорию или дефолтную "Прочее"
        Category category = categoryRepository.findByName(dto.getCategoryName())
                .orElseGet(() -> categoryRepository.findByName("Прочее")
                        .orElseThrow(() -> new EntityNotFoundException("Категория 'Прочее' не найдена")));

        Transaction transaction = new Transaction();
        transaction.setDate(dto.getDate());
        transaction.setAmount(dto.getAmount());

        // Преобразуем строки в enum
        try {
            transaction.setType(TransactionType.valueOf(dto.getType().toUpperCase()));
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Некорректный тип транзакции: " + dto.getType());
        }

        try {
            transaction.setSource(SourceType.valueOf(dto.getSource().toUpperCase()));
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Некорректный источник транзакции: " + dto.getSource());
        }

        transaction.setCategory(category);
        transaction.setComment(dto.getComment());

        return transactionRepository.save(transaction);
    }

}
