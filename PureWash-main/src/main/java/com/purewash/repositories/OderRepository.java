package com.purewash.repositories;

import com.purewash.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OderRepository extends JpaRepository<Order, UUID> {
        Optional<Order> findByCode(String code);
//        Optional<Order> findByMachineId(UUID machineId);
}
