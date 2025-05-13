package com.bank.backend.mapper;

import com.bank.backend.dto.TransactionDTO;
import com.bank.backend.model.Transaction;

public class TransactionMapper {
    private TransactionMapper() {}

    public static TransactionDTO dtoMapper(Transaction transaction) {
        return new TransactionDTO(
                transaction.getTimestamp(),
                transaction.getMode(),
                transaction.getAmount()
        );
    }
}
