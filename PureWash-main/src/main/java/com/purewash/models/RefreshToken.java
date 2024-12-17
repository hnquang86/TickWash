package com.purewash.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "refresh_token",nullable = false, unique = true, length = 300)
    private String refreshtoken;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    @Column(name = "is_revoked", nullable = false)
    private boolean isRevoked = false;
}