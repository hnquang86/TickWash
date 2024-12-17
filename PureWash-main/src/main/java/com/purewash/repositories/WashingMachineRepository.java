package com.purewash.repositories;


import com.purewash.models.WashingMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WashingMachineRepository extends JpaRepository<WashingMachine, UUID> {
    Optional<WashingMachine> findById(UUID id);

    WashingMachine findByStatusMachine(String statusMachine);
}
