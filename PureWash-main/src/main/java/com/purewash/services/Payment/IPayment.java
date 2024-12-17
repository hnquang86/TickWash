package com.purewash.services.Payment;


import com.purewash.models.Payment;

import java.util.List;
import java.util.UUID;

public interface IPayment {
    Payment createPayment(Payment payment);

    List<Payment> getPaymentsByUser(UUID userId);

    Payment getPaymentById(UUID paymentId);

    List<Payment> getPaymentsByStatus(String status);

    Payment updatePaymentStatus(UUID paymentId, String status);

    void deletePayment(UUID paymentId);
}
