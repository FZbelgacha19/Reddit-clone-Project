package com.apps.redditclonebackend.service;
import com.apps.redditclonebackend.dto.LoginRequest;
import com.apps.redditclonebackend.dto.RegisterRequest;
import com.apps.redditclonebackend.exception.SpringRedditException;
import com.apps.redditclonebackend.model.*;
import com.apps.redditclonebackend.repository.UserRepository;
import com.apps.redditclonebackend.repository.TokenVerificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenVerificationRepository tokenVerificationRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;

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
        mailService.sendMail(new NotificationEmail("Activer votre compte", user.getEmail(),
                "Merci d'activer votre compte clique sur le lien ci-dessous\n"
        +"http://localhost:8080/api/auth/accountVerification/"+token));
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

    public void verifyAccount(String token) {
        Optional<TokenVerification> verificationToken = tokenVerificationRepository.findByToken(token);
        verificationToken.orElseThrow(()-> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    public void fetchUserAndEnable(TokenVerification tokenVerification) {
        Long user_id = tokenVerification.getUser().getUserId();
        Optional<User> user = userRepository.findById(user_id);
        user.orElseThrow(
                () -> new SpringRedditException("User n'exist pas")
        );
        User u = user.get();
        u.setEnabled(true);
        userRepository.save(u);
    }

    public void login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    }
}
