package com.zxdesruc.budgettracker.service;

import com.zxdesruc.budgettracker.model.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing financial transactions.
 */
public interface TransactionService {

    Transaction createTransaction(Transaction transaction);

    Optional<Transaction> getTransactionById(Long id);

    List<Transaction> getAllTransactions();

    List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate);

    Transaction updateTransaction(Long id, Transaction updatedTransaction);

    void deleteTransaction(Long id);
}
