package com.apps.redditclonebackend.service;
import com.apps.redditclonebackend.dto.RegisterRequest;
import com.apps.redditclonebackend.model.*;
import com.apps.redditclonebackend.repository.UserRepository;
import com.apps.redditclonebackend.repository.TokenVerificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;


@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenVerificationRepository tokenVerificationRepository;


    @Transactional
    public void signup(RegisterRequest regRequest){
        User user = User.builder()
                .username(regRequest.getUsername())
                .email(regRequest.getEmail())
                .password(passwordEncoder.encode(regRequest.getPassword()))
                .created(Instant.now())
                .enabled(false)
                .build();
        userRepository.save(user);

        // the system will generate a token to enable sigin-in of the user in apps

        String token = generateVerificationToken(user);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        TokenVerification vt = TokenVerification.builder()
                .token(token)
                .user(user)
                .build();
        tokenVerificationRepository.save(vt);
        return token;
    }
}
