package com.apps.redditclonebackend.model;

import java.time.Instant;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * la classe VerificationToken est utilisée pour gérer les tokens de vérification de compte
 */

@Entity @Data @Builder @AllArgsConstructor @NoArgsConstructor
public class VerificationToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    private Instant expiryDate;
}
