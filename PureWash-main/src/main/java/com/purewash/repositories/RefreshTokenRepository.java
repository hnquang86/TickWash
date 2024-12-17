package com.purewash.repositories;

import com.purewash.models.RefreshToken;
import com.purewash.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshtoken(String refreshToken);
    @Modifying
    int deleteByUser(User user);
}
