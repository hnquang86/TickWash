package com.purewash.responses.Subcription;

import com.purewash.models.Subcription;

import java.util.List;
import java.util.UUID;

public interface ISubcription {
    Subcription addSubscription(Subcription subscription);

    List<Subcription> getSubscriptionsByUserId(UUID userId);

    Subcription getSubscriptionById(UUID subscriptionId);

    List<Subcription> getActiveSubscriptions();

    Subcription updateSubscriptionStatus(UUID subscriptionId, Boolean isActive);

    void deleteSubscription(UUID subscriptionId);
}
