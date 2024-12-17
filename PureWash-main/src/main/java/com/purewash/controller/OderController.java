package com.purewash.controller;


import com.purewash.models.Order;
import com.purewash.responses.APIResponse;
import com.purewash.services.Order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<APIResponse<Order>> createOrder(@RequestParam UUID machineId, @RequestParam int totalTime) {
        Order order = orderService.createOrder(machineId, totalTime);
        return ResponseEntity.ok(new APIResponse<>(order, "Tạo order thành công"));
    }

    @PostMapping("/invalidate")
    public ResponseEntity<APIResponse<Void>> invalidateOrder(@RequestParam String code) {
        orderService.invalidateOrder(code);
        return ResponseEntity.ok(new APIResponse<>(null, "Hủy hiệu lực thành công"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<APIResponse<Void>> deleteOrder(@RequestParam String code) {
        orderService.deleteOrderByCode(code);
        return ResponseEntity.ok(new APIResponse<>(null, "Xóa order thành công"));
    }

    @PostMapping("/updateCurrent")
    public ResponseEntity<APIResponse<Void>> updateOrderCurrent(@RequestParam UUID machineId, @RequestParam double current) {
        orderService.updateOrderWithCurrent(machineId, current);
        return ResponseEntity.ok(new APIResponse<>(null, "Cập nhật thành công"));
    }
}
