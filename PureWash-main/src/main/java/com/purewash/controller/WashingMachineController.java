package com.purewash.controller;

import com.purewash.models.WashingMachine;
import com.purewash.services.WashingMachine.IWashingMachineService;
import com.purewash.services.WashingMachine.WashingMachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/washing-machines")
public class WashingMachineController {
    private final WashingMachineService washingMachineService;

    @GetMapping("/{id}")
    public ResponseEntity<WashingMachine> getWashingMachineById(@PathVariable UUID id) {
        WashingMachine machine = washingMachineService.getWashingMachineById(id);
        return ResponseEntity.ok(machine);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<WashingMachine> updateStatus(
            @PathVariable UUID id,
            @RequestParam String status) {
        WashingMachine updatedMachine = washingMachineService.updateStatus(id, status);
        return ResponseEntity.ok(updatedMachine);
    }

    @PostMapping("/{id}/esp-response")
    public ResponseEntity<Void> handleEsp32Response(
            @PathVariable UUID id,
            @RequestParam double current,
            @RequestParam String status) {
        washingMachineService.handleEsp32Response(id, current, status);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/invalidate-code")
    public ResponseEntity<Void> handleCodeInvalidation(
            @PathVariable UUID id,
            @RequestParam String code) {
        washingMachineService.handleCodeInvalidation(id, code);
        return ResponseEntity.ok().build();
    }
}