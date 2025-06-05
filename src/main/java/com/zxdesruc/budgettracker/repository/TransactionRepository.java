package com.zxdesruc.budgettracker.repository;

import com.zxdesruc.budgettracker.model.Transaction;
import com.zxdesruc.budgettracker.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for accessing Transaction entities.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Find all transactions by category.
     *
     * @param category the transaction category
     * @return list of transactions matching the category
     */
    List<Transaction> findByCategory(String category);

    /**
     * Find all transactions between two dates.
     *
     * @param start the start date
     * @param end   the end date
     * @return list of transactions in the date range
     */
    List<Transaction> findByDateBetween(LocalDate start, LocalDate end);

    /**
     * Find all transactions by type.
     *
     * @param type the transaction type
     * @return list of transactions of that type
     */
    List<Transaction> findByType(String type);

    /**
     * Find transactions by category and date range and type.
     *
     * @param category category or null
     * @param start start date or null
     * @param end end date or null
     * @param type type or null
     * @return matching transactions
     */
    List<Transaction> findByCategoryAndDateBetweenAndType(String category, LocalDate start, LocalDate end, String type);

    List<Transaction> findByTypeAndDateBetween(TransactionType type, LocalDate start, LocalDate end);

}
