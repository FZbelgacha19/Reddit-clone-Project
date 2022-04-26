package com.apps.redditclonebackend.service;
import com.apps.redditclonebackend.dto.AuthenticationResponse;
import com.apps.redditclonebackend.dto.LoginRequest;
import com.apps.redditclonebackend.dto.RefreshTokenRequest;
import com.apps.redditclonebackend.dto.RegisterRequest;
import com.apps.redditclonebackend.exception.SpringRedditException;
import com.apps.redditclonebackend.exception.UserException;
import com.apps.redditclonebackend.model.*;
import com.apps.redditclonebackend.repository.UserRepository;
import com.apps.redditclonebackend.repository.TokenVerificationRepository;
import com.apps.redditclonebackend.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.oauth2.jwt.Jwt;

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
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

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
        String token = UUID.randomUUID().toString(); // UUID est une classe de java qui génère des ID aléatoires
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

    public AuthenticationResponse login(LoginRequest loginRequest) {
        /*
         UsernamePasswordAuthenticationToken() : c'est une classe de spring qui permet de créer un objet de type UsernamePasswordAuthenticationToken qui contient les informations de l'utilisateur qui se connecte (username, password) et qui est ensuite envoyé à l'authenticationManager
         qui va vérifier si le couple username/password est correct et si oui, le token est créé et envoyé à l'utilisateur (ce token est utilisé pour se connecter à l'application)
         */
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        /*
        SecurityContextHolder : c'est une classe de spring qui permet de stocker le token dans le contexte de l'application
        c'est quoi un contexte ?
        Il s'agit d'un objet qui contient toutes les informations de l'utilisateur connecté (token, roles, etc...)
         */
        String token = jwtProvider.generateToken(authentication);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject())
                .orElseThrow(() -> new UserException("Ce nom n'existe pas - " + principal.getSubject()));
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }
}
