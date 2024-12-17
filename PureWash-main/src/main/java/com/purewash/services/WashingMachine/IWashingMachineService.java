package com.purewash.services.WashingMachine;


import com.purewash.models.WashingMachine;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface IWashingMachineService {
    WashingMachine updateStatus(UUID machineId, String status);
    WashingMachine getWashingMachineById(UUID machineId);
    void handleEsp32Response(UUID machineId, double current, String status);
    void handleCodeInvalidation(UUID machineId, String code);


}
