package com.purewash.responses.Subcription;


import com.purewash.models.Subcription;
import com.purewash.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubcriptionService implements ISubcription{
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subcription addSubscription(Subcription subscription) {
        subscription.setIsActive(true);
        return subscriptionRepository.save(subscription);    }

    @Override
    public List<Subcription> getSubscriptionsByUserId(UUID userId) {
//        return subscriptionRepository.findByUserId(userId);
        return null;
    }

    @Override
    public Subcription getSubscriptionById(UUID subscriptionId) {
        return subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy gói đăng ký với ID: " + subscriptionId));
    }


    @Override
    public List<Subcription> getActiveSubscriptions() {
        return subscriptionRepository.findByIsActiveTrue();
    }

    @Override
    public Subcription updateSubscriptionStatus(UUID subscriptionId, Boolean isActive) {
        Subcription subscription = getSubscriptionById(subscriptionId);
        subscription.setIsActive(isActive);
        return subscriptionRepository.save(subscription);
    }

    @Override
    public void deleteSubscription(UUID subscriptionId) {
        Subcription subscription = getSubscriptionById(subscriptionId);
        subscriptionRepository.delete(subscription);
    }
}
