package com.dashcrypto.login.repositories;

import com.dashcrypto.login.models.entities.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, UUID> {
}
