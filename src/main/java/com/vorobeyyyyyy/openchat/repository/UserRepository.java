package com.vorobeyyyyyy.openchat.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vorobeyyyyyy.openchat.model.domain.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByMobilePhone(String phone);

	User findByUuid(UUID uuid);
}
