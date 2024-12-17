package com.purewash.services.Payment;

import com.purewash.models.Payment;
import com.purewash.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPayment{
    private final PaymentRepository paymentRepository;

    @Override
    public Payment createPayment(Payment payment) {
        payment.setPaymentDate(new Date());
        payment.setStatus("PENDING");
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getPaymentsByUser(UUID userId) {
        return paymentRepository.findByUserId(userId);
    }

    @Override
    public Payment getPaymentById(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thanh toán với ID: " + paymentId));
    }

    @Override
    public List<Payment> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(status);
    }

    @Override
    public Payment updatePaymentStatus(UUID paymentId, String status) {
        Payment payment = getPaymentById(paymentId);
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(UUID paymentId) {
        Payment payment = getPaymentById(paymentId);
        paymentRepository.delete(payment);
    }

}
