package com.apps.redditclonebackend.repository;

import com.apps.redditclonebackend.model.TokenVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenVerificationRepository extends JpaRepository<TokenVerification, Long>{
}
