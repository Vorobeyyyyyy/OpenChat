package com.vorobeyyyyyy.openchat.repository;

import com.vorobeyyyyyy.openchat.model.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
}