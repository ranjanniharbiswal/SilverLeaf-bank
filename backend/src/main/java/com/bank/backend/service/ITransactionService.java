package com.bank.backend.service;

import com.bank.backend.constant.TransactionMode;
import com.bank.backend.model.Transaction;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ITransactionService {
    void addTransaction(Transaction transaction, long accountNumber);

    CompletableFuture<List<Transaction>> getDebitedTransactions(long accountNumber, int pageSize, int pageNumber);

    List<Transaction> getCreditedTransactions(long accountNumber, int pageSize, int pageNumber);

    List<Transaction> getTransferredTransactions(long accountNumber, int pageSize, int pageNumber);

    CompletableFuture<List<Transaction>> getLast5Transaction(long accountNumber);

    long countRecord(TransactionMode mode, long accountNumber);

    CompletableFuture<Long> countRecord1(TransactionMode mode, long accountNumber);
}
