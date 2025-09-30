package com.arnavgautam.taskmanager.repository;

import com.arnavgautam.taskmanager.entity.RefreshToken;
import com.arnavgautam.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

/**
 * Repository interface for RefreshToken entity
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    /**
     * Find refresh token by token string
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Delete all refresh tokens for a user
     */
    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.user = :user")
    void deleteByUser(@Param("user") User user);

    /**
     * Delete expired refresh tokens
     */
    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.expiryDate < :now")
    void deleteAllExpiredTokens(@Param("now") Instant now);
}
