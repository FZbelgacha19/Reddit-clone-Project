package com.apps.redditclonebackend.controller;


import com.apps.redditclonebackend.dto.AuthenticationResponse;
import com.apps.redditclonebackend.dto.LoginRequest;
import com.apps.redditclonebackend.dto.RegisterRequest;
import com.apps.redditclonebackend.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> singup(@RequestBody RegisterRequest regRequest){
        System.out.println("hello");
        authService.signup(regRequest);
        return new ResponseEntity<>("user register successful",HttpStatus.OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("account verification successful",HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}
