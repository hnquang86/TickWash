package com.purewash.repositories;


import com.purewash.models.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TranSactionHistoryRepository extends JpaRepository<TransactionHistory, UUID> {
    List<TransactionHistory> findByUserId(UUID userId);


}
