package com.purewash.repositories;

import com.purewash.models.Subcription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface SubscriptionRepository extends JpaRepository<Subcription, UUID> {
//    List<Subcription> findByUserId(UUID userId);

    List<Subcription> findByIsActiveTrue();
}
