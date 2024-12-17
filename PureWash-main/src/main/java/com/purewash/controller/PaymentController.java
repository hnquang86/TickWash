package com.purewash.controller;

import com.purewash.models.Payment;
import com.purewash.responses.APIResponse;
import com.purewash.services.Payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<APIResponse<Payment>> createPayment(@RequestBody Payment payment) {
        Payment createdPayment = paymentService.createPayment(payment);
        return ResponseEntity.ok(new APIResponse<>(createdPayment, "Tạo thanh toán thành công"));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<APIResponse<List<Payment>>> getPaymentsByUser(@PathVariable UUID userId) {
        List<Payment> payments = paymentService.getPaymentsByUser(userId);
        return ResponseEntity.ok(new APIResponse<>(payments, "Lấy danh sách thanh toán thành công"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<Payment>> getPaymentById(@PathVariable UUID id) {
        Payment payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(new APIResponse<>(payment, "Lấy chi tiết thanh toán thành công"));
    }

    @GetMapping("/status")
    public ResponseEntity<APIResponse<List<Payment>>> getPaymentsByStatus(@RequestParam String status) {
        List<Payment> payments = paymentService.getPaymentsByStatus(status);
        return ResponseEntity.ok(new APIResponse<>(payments, "Lấy danh sách thanh toán theo trạng thái thành công"));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<APIResponse<Payment>> updatePaymentStatus(
            @PathVariable UUID id, @RequestParam String status) {
        Payment updatedPayment = paymentService.updatePaymentStatus(id, status);
        return ResponseEntity.ok(new APIResponse<>(updatedPayment, "Cập nhật trạng thái thanh toán thành công"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deletePayment(@PathVariable UUID id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok(new APIResponse<>(null, "Xóa thanh toán thành công"));
    }
}