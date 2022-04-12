package com.apps.redditclonebackend.repository;

import com.apps.redditclonebackend.model.TokenVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenVerificationRepository extends JpaRepository<TokenVerification, Long>{
    Optional<TokenVerification> findByToken(String token);
}
