package com.purewash.services.WashingMachine;

import com.purewash.models.WashingMachine;
import com.purewash.repositories.WashingMachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WashingMachineService implements IWashingMachineService {
    private final WashingMachineRepository washingMachineRepository;


    @Override
    public WashingMachine updateStatus(UUID machineId, String status) {
        Optional<WashingMachine> washingMachineOptional = washingMachineRepository.findById(machineId);

        if (washingMachineOptional.isPresent()) {
            WashingMachine washingMachine = washingMachineOptional.get();
            washingMachine.setStatusMachine(status);
            return washingMachineRepository.save(washingMachine);
        } else {
            throw new IllegalArgumentException("Không tìm thấy máy giặt với ID: " + machineId);
        }
    }

    @Override
    public WashingMachine getWashingMachineById(UUID machineId) {
        return washingMachineRepository.findById(machineId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy máy giặt với ID: " + machineId));
    }

    @Override
    public void handleEsp32Response(UUID machineId, double current, String status) {
        WashingMachine washingMachine = washingMachineRepository.findById(machineId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy máy giặt với ID: " + machineId));

        washingMachine.setStatusMachine(status);
        washingMachineRepository.save(washingMachine);

        throw new IllegalStateException("Cường độ dòng điện không hợp lệ từ ESP32: " + current);
    }

    @Override
    public void handleCodeInvalidation(UUID machineId, String code) {
        throw new IllegalStateException("ESP32 báo mã code hết hiệu lực: " + code + " trên máy giặt ID: " + machineId);
    }
}
