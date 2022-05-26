package com.apps.redditclonebackend.security;
import com.apps.redditclonebackend.exception.SpringRedditException;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtEncoder jwtEncoder;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return generateTokenWithUserName(principal.getUsername());
    }

    public String generateTokenWithUserName(String username) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self") // qui créer le token
                .issuedAt(Instant.now()) // quand il a été créé
                .expiresAt(Instant.now().plusMillis(jwtExpirationInMillis)) // quand il expire
                .subject(username) // qui est le sujet du token
                .claim("scope", "ROLE_USER") // qui a les droits
                .build(); // construit le token

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
/*
  JWT est un standard de cryptage pour les applications web.
 * Il permet de générer un jeton d'authentification pour les utilisateurs.
 * Il permet de décoder un jeton d'authentification pour vérifier qu'il est valide.
 * il consiste en un ensemble de claims (champs) qui sont définis par le service d'authentification.
 * il compose de trois parties :
 * un header (qui contient les informations sur le jeton)
 * un payload (qui contient les informations sur l'utilisateur)
 * un signature (qui contient le jeton en clair)
 *
 * exemple :
 * header : {
 *         "alg": "HS256",
 *         "typ": "JWT"
 * }
 * payload : {
 *         "iss": "self",
 *         "iat": 1516239022, // issued at
 *         "exp": 1516247022,
 *         "sub": "janeDoe",
 *         "scope": "ROLE_USER"
 *         }
 * signature :
 * HMACHash(header.base64UrlEncode() + "." + payload.base64UrlEncode() + "." + secret)
 *
 * result :
 */