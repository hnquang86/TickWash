package com.purewash.services.TransactionHistory;

import com.purewash.models.TransactionHistory;

import java.util.List;
import java.util.UUID;

public interface ITransactionHistorySevice {
    TransactionHistory addTransaction(TransactionHistory transactionHistory);

    List<TransactionHistory> getTransactionsByUserId(UUID userId);

    TransactionHistory getTransactionById(UUID transactionId);

    void deleteTransaction(UUID transactionId);
}
