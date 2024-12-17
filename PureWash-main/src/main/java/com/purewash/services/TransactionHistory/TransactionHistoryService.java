package com.purewash.services.TransactionHistory;

import com.purewash.models.TransactionHistory;
import com.purewash.repositories.TranSactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@RequiredArgsConstructor
@Service
public class TransactionHistoryService implements ITransactionHistorySevice {
    private final TranSactionHistoryRepository transactionHistoryRepository;

    @Override
    public TransactionHistory addTransaction(TransactionHistory transactionHistory) {
        transactionHistory.setTransactionDate(new java.util.Date());
        return null;
//        return transactionHistoryRepository.save(transactionHistory);
    }

    @Override
    public List<TransactionHistory> getTransactionsByUserId(UUID userId) {
        return transactionHistoryRepository.findByUserId(userId);
    }

    @Override
    public TransactionHistory getTransactionById(UUID transactionId) {
        return null;
    }

    @Override
    public void deleteTransaction(UUID transactionId) {
        transactionHistoryRepository.deleteById(transactionId);
    }
}
