package com.purewash.services.Order;


import com.purewash.dto.User.Order.OrderDTO;
import com.purewash.models.Order;
import com.purewash.repositories.OderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OderRepository oderRepository;


    @Override
    public Order createOrder(UUID machineId, int totalTime) {
        String randomCode = generateRandomCode(machineId);

        Order order = Order.builder()
                .id(UUID.randomUUID())
                .startTime(null) // Để trống, cập nhật sau
                .endTime(null)   // Để trống, cập nhật sau
                .status("PENDING") // Trạng thái mặc định là chờ
                .code(randomCode)  // Mã code sinh ra
                .validTime(true)   // Đặt hiệu lực mặc định là true
                .totalTime(totalTime) // Tổng thời gian đặt
                .build();

        return oderRepository.save(order);
    }

    @Override
    public String generateRandomCode(UUID machineId) {
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder(machineId.toString().substring(0, 1)); // Lấy ký tự đầu làm mã máy

        for (int i = 0; i < 7; i++) {
            codeBuilder.append(random.nextInt(10));
        }

        String generatedCode = codeBuilder.toString();
        return generatedCode;
    }

    @Override
    public Order getOrderByCode(String code) {
        return oderRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy order với mã code: " + code));
    }

    @Override
    public void invalidateOrder(String code) {
        Optional<Order> optionalOrder = oderRepository.findByCode(code);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setValidTime(false);
            oderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Không tìm thấy order với mã code: " + code);
        }
    }

    @Override
    public void updateOrderWithCurrent(UUID machineId, double current) {
//        Optional<Order> optionalOrder = oderRepository.findByMachineId(machineId);
//        if (optionalOrder.isPresent()) {
//            Order order = optionalOrder.get();
//            // Cập nhật logic liên quan đến dòng điện (nếu có)
//            oderRepository.save(order);
//        } else {
//            throw new IllegalArgumentException("Không tìm thấy order với machineId: " + machineId);
//        }
    }

    @Override
    public void deleteOrderByCode(String code) {
        Optional<Order> optionalOrder = oderRepository.findByCode(code);
        if (optionalOrder.isPresent()) {
            oderRepository.delete(optionalOrder.get());
        } else {
            throw new IllegalArgumentException("Không tìm thấy order với mã code: " + code);
        }
    }
}
