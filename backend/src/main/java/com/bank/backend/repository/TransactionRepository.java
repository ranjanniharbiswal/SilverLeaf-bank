package com.bank.backend.repository;

import com.bank.backend.constant.TransactionMode;
import com.bank.backend.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction>
     findByModeAndAccountAccountNumber(TransactionMode mode, long accountNumber,
                                       Pageable pageable);

    List<Transaction>
     findTop5ByAccountAccountNumberOrderByTimestampDesc(long accountNumber);

    long
    countByModeAndAccountAccountNumber(TransactionMode mode, long accountNumber);
}
