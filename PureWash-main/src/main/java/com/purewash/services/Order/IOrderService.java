package com.purewash.services.Order;

import com.purewash.dto.User.Order.OrderDTO;
import com.purewash.models.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IOrderService {
    Order createOrder(UUID machineId, int totalTime); // Tạo đơn đặt hàng mới

    String generateRandomCode(UUID machineId); // Sinh mã code cho máy

    Order getOrderByCode(String code); // Lấy order theo code

    void invalidateOrder(String code); // Hủy hiệu lực của mã code

    void updateOrderWithCurrent(UUID machineId, double current); // Cập nhật thông tin dòng điện

    void deleteOrderByCode(String code); // Xóa order theo code
}
