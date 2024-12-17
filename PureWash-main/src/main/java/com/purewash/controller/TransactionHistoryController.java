package com.purewash.controller;

import com.purewash.models.TransactionHistory;
import com.purewash.responses.APIResponse;
import com.purewash.services.TransactionHistory.TransactionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transaction-history")
@RequiredArgsConstructor
public class TransactionHistoryController {
    private final TransactionHistoryService transactionHistoryService;

    @PostMapping
    public ResponseEntity<APIResponse<TransactionHistory>> createTransaction(@RequestBody TransactionHistory transactionHistory) {
        TransactionHistory savedTransaction = transactionHistoryService.addTransaction(transactionHistory);
        return ResponseEntity.ok(new APIResponse<>(savedTransaction, "Giao dịch được thêm thành công"));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<APIResponse<List<TransactionHistory>>> getTransactionsByUser(@PathVariable UUID userId) {
        List<TransactionHistory> transactions = transactionHistoryService.getTransactionsByUserId(userId);
        return ResponseEntity.ok(new APIResponse<>(transactions, "Lấy danh sách giao dịch thành công"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<TransactionHistory>> getTransactionById(@PathVariable UUID id) {
        TransactionHistory transaction = transactionHistoryService.getTransactionById(id);
        return ResponseEntity.ok(new APIResponse<>(transaction, "Lấy chi tiết giao dịch thành công"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteTransaction(@PathVariable UUID id) {
        transactionHistoryService.deleteTransaction(id);
        return ResponseEntity.ok(new APIResponse<>(null, "Xóa giao dịch thành công"));
    }
}
