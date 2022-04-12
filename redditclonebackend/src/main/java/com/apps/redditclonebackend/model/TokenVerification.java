package com.apps.redditclonebackend.model;

import java.time.Instant;

import javax.persistence.*;

import lombok.*;

/**
 * la classe VerificationToken est utilisée pour gérer les tokens de vérification de compte
 */

@Entity @Builder
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class TokenVerification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Instant expiryDate;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

}
