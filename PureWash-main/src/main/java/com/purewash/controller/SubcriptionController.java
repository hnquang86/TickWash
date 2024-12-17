package com.purewash.controller;

import com.purewash.models.Subcription;
import com.purewash.responses.APIResponse;
import com.purewash.responses.Subcription.SubcriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubcriptionController {

    private final SubcriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<APIResponse<Subcription>> createSubscription(@RequestBody Subcription subscription) {
        Subcription createdSubscription = subscriptionService.addSubscription(subscription);
        return ResponseEntity.ok(new APIResponse<>(createdSubscription, "Tạo gói đăng ký thành công"));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<APIResponse<List<Subcription>>> getSubscriptionsByUser(@PathVariable UUID userId) {
//        List<Subcription> subscriptions = subscriptionService.getSubscriptionsByUserId(userId);
//        return ResponseEntity.ok(new APIResponse<>(subscriptions, "Lấy danh sách gói đăng ký thành công"));
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<Subcription>> getSubscriptionById(@PathVariable UUID id) {
        Subcription subscription = subscriptionService.getSubscriptionById(id);
        return ResponseEntity.ok(new APIResponse<>(subscription, "Lấy chi tiết gói đăng ký thành công"));
    }

    @GetMapping("/active")
    public ResponseEntity<APIResponse<List<Subcription>>> getActiveSubscriptions() {
        List<Subcription> activeSubscriptions = subscriptionService.getActiveSubscriptions();
        return ResponseEntity.ok(new APIResponse<>(activeSubscriptions, "Lấy danh sách gói đăng ký còn hiệu lực"));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<APIResponse<Subcription>> updateSubscriptionStatus(
            @PathVariable UUID id, @RequestParam Boolean isActive) {
        Subcription updatedSubscription = subscriptionService.updateSubscriptionStatus(id, isActive);
        return ResponseEntity.ok(new APIResponse<>(updatedSubscription, "Cập nhật trạng thái gói đăng ký thành công"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteSubscription(@PathVariable UUID id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.ok(new APIResponse<>(null, "Xóa gói đăng ký thành công"));
    }
}